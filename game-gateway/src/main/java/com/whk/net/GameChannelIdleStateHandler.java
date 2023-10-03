package com.whk.net;

import com.whk.net.channel.AbstractGameChannelHandlerContext;
import com.whk.net.channel.GameChannelInboundHandler;
import com.whk.net.channel.GameChannelOutboundHandler;
import com.whk.net.channel.GameChannelPromise;
import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;


import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameChannelIdleStateHandler implements GameChannelInboundHandler, GameChannelOutboundHandler {

    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);// 延迟事件的延迟时间的最小值
    private final long readerIdleTimeNanos;// 读取消息的空闲时间，单位纳秒
    private final long writerIdleTimeNanos;// 写出消息的空闲时间，单位纳秒
    private final long allIdleTimeNanos; // 读取和写出消息的空闲时间，单位纳秒

    private ScheduledFuture<?> readerIdleTimeout; // 读取消息的超时延时检测事件
    private long lastReadTime; // 最近一次读取消息的时间
    private ScheduledFuture<?> writerIdleTimeout; // 写出消息的超时延时检测事件
    private long lastWriteTime; // 最近一次写出消息的时间
    private ScheduledFuture<?> allIdleTimeout; // 读写消息的超时检测事件
    private byte state; // 0 - none, 1 - initialized, 2 - destroyed
    // 要不然会有这样的情况，虽然GameChannel已被移除，但是当定时事件执行时，又会创建一个新的定时事件，导致这个对象不会被回收

    public GameChannelIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {

        this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds, TimeUnit.SECONDS);
    }

    public GameChannelIdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (readerIdleTime <= 0) {
            readerIdleTimeNanos = 0;
        } else {
            readerIdleTimeNanos = Math.max(unit.toNanos(readerIdleTime), MIN_TIMEOUT_NANOS);
        }
        if (writerIdleTime <= 0) {
            writerIdleTimeNanos = 0;
        } else {
            writerIdleTimeNanos = Math.max(unit.toNanos(writerIdleTime), MIN_TIMEOUT_NANOS);
        }
        if (allIdleTime <= 0) {
            allIdleTimeNanos = 0;
        } else {
            allIdleTimeNanos = Math.max(unit.toNanos(allIdleTime), MIN_TIMEOUT_NANOS);
        }
    }

    @Override
    public void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRegister(AbstractGameChannelHandlerContext ctx, Long playerId, GameChannelPromise promise) {
        initialize(ctx);
        ctx.fireChannelRegistered(playerId, promise);
    }

    @Override
    public void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception {
        destroy();
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(AbstractGameChannelHandlerContext ctx, MessageWrapperOuterClass.MessageWrapper msg) throws Exception {
        if (readerIdleTimeNanos > 0 || allIdleTimeNanos > 0) {
            this.lastReadTime = this.ticksInNanos();// 记录最后一次读取操作的时间
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageWrapperOuterClass.MessageWrapper msg, GameChannelPromise promise) throws Exception {
        if (writerIdleTimeNanos > 0 || allIdleTimeNanos > 0) {
            this.lastWriteTime = this.ticksInNanos();
        }
        ctx.writeAndFlush(msg, promise);
    }

    @Override
    public void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise) {
        ctx.close(promise);
    }

    private void initialize(AbstractGameChannelHandlerContext ctx) {
        state = 1;
        lastReadTime = lastWriteTime = ticksInNanos();
        if (readerIdleTimeNanos > 0) {
            // 初始化创建读取消息事件检测延时任务
            readerIdleTimeout = schedule(ctx, new ReaderIdleTimeoutTask(ctx), readerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
//        if (writerIdleTimeNanos > 0) {
//            // 初始化创建写出消息事件检测延时任务
//            writerIdleTimeout = schedule(ctx, new WriterIdleTimeoutTask(ctx), writerIdleTimeNanos, TimeUnit.NANOSECONDS);
//        }
//        if (allIdleTimeNanos > 0) {
//            // 初始化创建读取和写出消息事件检测延时任务
//            allIdleTimeout = schedule(ctx, new AllIdleTimeoutTask(ctx), allIdleTimeNanos, TimeUnit.NANOSECONDS);
//        }
    }

    /**
     * 获取当前时间的纳秒
     *
     * @return
     */
    long ticksInNanos() {
        return System.nanoTime();
    }

    ScheduledFuture<?> schedule(AbstractGameChannelHandlerContext ctx, Runnable task, long delay, TimeUnit unit) {
        return ctx.executor().schedule(task, delay, unit);// 创建延时任务
    }

    /**
     * 销毁定时事件任务
     */
    private void destroy() {
        state = 2;
        if (readerIdleTimeout != null) {
            readerIdleTimeout.cancel(false);
            readerIdleTimeout = null;
        }
        if (writerIdleTimeout != null) {
            writerIdleTimeout.cancel(false);
            writerIdleTimeout = null;
        }
        if (allIdleTimeout != null) {
            allIdleTimeout.cancel(false);
            allIdleTimeout = null;
        }
    }

    private abstract static class AbstractIdleTask implements Runnable {// 公共抽象任务

        private final AbstractGameChannelHandlerContext ctx;

        AbstractIdleTask(AbstractGameChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (!ctx.gameChannel().isRegistered()) {
                return;
            }

            run(ctx);
        }

        protected abstract void run(AbstractGameChannelHandlerContext ctx);
    }

    private final class ReaderIdleTimeoutTask extends AbstractIdleTask {// 读取消息检测任务

        ReaderIdleTimeoutTask(AbstractGameChannelHandlerContext ctx) {
            super(ctx);
        }

        @Override
        protected void run(AbstractGameChannelHandlerContext ctx) {
            long nextDelay = readerIdleTimeNanos;
            nextDelay -= ticksInNanos() - lastReadTime;
            if (nextDelay <= 0) {
                // 说明读取事件超时，关闭
                try {
                    ctx.close(ctx.newPromise());
//                    IdleStateEvent event = newIdleStateEvent(IdleState.READER_IDLE);
//                    channelIdle(ctx, event);
                } catch (Throwable t) {
                    ctx.fireExceptionCaught(t);
                }
            } else {
                // 没有超时，从上次读取的时间起，计时计算下次超时检测
                readerIdleTimeout = schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }
}
