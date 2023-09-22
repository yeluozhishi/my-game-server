package com.whk.net.channel;

import com.whk.net.enity.Message;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public abstract class AbstractGameChannelHandlerContext {

    static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);

    volatile AbstractGameChannelHandlerContext next;
    volatile AbstractGameChannelHandlerContext prev;

    private final boolean inbound;

    private final boolean outbound;

    private final GameChannelPipeline pipeline;

    final EventExecutor executor;

    private String name;

    public AbstractGameChannelHandlerContext(boolean inbound, boolean outbound, GameChannelPipeline pipeline, EventExecutor executor, String name) {
        this.inbound = inbound;
        this.outbound = outbound;
        this.pipeline = pipeline;
        this.executor = executor;
        this.name = ObjectUtil.checkNotNull(name, "name");
    }

    public abstract GameChannelHandler handler();

    public GameChannel gameChannel() {
        return pipeline.gameChannel();
    }

    public String getName() {
        return name;
    }

    public AbstractGameChannelHandlerContext fireChannelInactive() {
        invokeChannelInactive(findContextInbound());
        return this;
    }

    static void invokeChannelInactive(final AbstractGameChannelHandlerContext next) {
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelInactive();
        } else {
            executor.execute(() -> next.invokeChannelInactive());
        }
    }

    public EventExecutor executor() {
        if (executor == null) {
            return gameChannel().executor();
        } else {
            return executor;
        }
    }


    private AbstractGameChannelHandlerContext findContextInbound() {
        AbstractGameChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.inbound);
        return ctx;
    }

    private AbstractGameChannelHandlerContext findContextOutbound() {
        AbstractGameChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!ctx.outbound);
        return ctx;
    }

    private void invokeChannelInactive() {
        try {
            ((GameChannelInboundHandler) handler()).channelInactive(this);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

    private void notifyHandlerException(Throwable cause) {
        logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event " + cause);
        try {
            handler().exceptionCaught(this, cause);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractGameChannelHandlerContext fireExceptionCaught(Throwable cause) {
        invokeExceptionCaught(next, cause);
        return this;
    }

    static void invokeExceptionCaught(final AbstractGameChannelHandlerContext next, final Throwable cause) {
        ObjectUtil.checkNotNull(cause, "cause");
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeExceptionCaught(cause);
        } else {
            try {
                executor.execute(() -> next.invokeExceptionCaught(cause));
            } catch (Throwable t) {
                logger.warn("Failed to submit an exceptionCaught() event. " + t);
                logger.warn("The exceptionCaught() event that was failed to submit was: " + cause);
            }
        }
    }

    private void invokeExceptionCaught(final Throwable cause) {
        try {
            handler().exceptionCaught(this, cause);
        } catch (Throwable error) {
            logger.warn("An exception '{" + error + "}' [enable DEBUG level for full stacktrace] was thrown " +
                    "by a user handler's exceptionCaught() method while handling the following exception:" + cause);
        }

    }

    public AbstractGameChannelHandlerContext fireChannelRegistered(Long playerId, GameChannelPromise promise) {
        invokeChannelRegistered(findContextInbound(), playerId, promise);
        return this;
    }

    static void invokeChannelRegistered(final AbstractGameChannelHandlerContext next, Long playerId, GameChannelPromise promise) {
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelRegistered(playerId, promise);
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelRegistered(playerId, promise);
                }
            });
        }
    }

    private void invokeChannelRegistered(Long playerId, GameChannelPromise promise) {
        try {
            ((GameChannelInboundHandler) handler()).channelRegister(this, playerId, promise);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

    public AbstractGameChannelHandlerContext fireChannelRead(Message msg) {
        invokeChannelRead(findContextInbound(), msg);
        return this;
    }

    static void invokeChannelRead(final AbstractGameChannelHandlerContext next, final Message msg) {
        ObjectUtil.checkNotNull(msg, "msg");
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelRead(msg);
        } else {
            executor.execute(() -> next.invokeChannelRead(msg));
        }
    }

    private void invokeChannelRead(Message msg) {
        try {
            ((GameChannelInboundHandler) handler()).channelRead(this, msg);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }

    }

    public GameChannelFuture writeAndFlush(Message msg) {
        return writeAndFlush(msg, newPromise());
    }

    public GameChannelPromise newPromise() {
        return new DefaultGameChannelPromise(gameChannel(), this.executor());
    }

    public GameChannelFuture writeAndFlush(Message msg, GameChannelPromise promise) {
        AbstractGameChannelHandlerContext next = findContextOutbound();
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeWrite(msg, promise);
        } else {
            executor.execute(() -> next.invokeWrite(msg, promise));
        }
        return promise;
    }

    private void invokeWrite(Message msg, GameChannelPromise promise) {
        try {
            ((GameChannelOutboundHandler) handler()).writeAndFlush(this, msg, promise);
        } catch (Throwable t) {
            notifyOutboundHandlerException(t, promise);
        }
    }

    private static void notifyOutboundHandlerException(Throwable cause, Promise<?> promise) {
        PromiseNotificationUtil.tryFailure(promise, cause, logger);
    }

    public GameChannelFuture close(final GameChannelPromise promise) {
        final AbstractGameChannelHandlerContext next = findContextOutbound();
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeClose(promise);
        } else {
            safeExecute(executor, () -> next.invokeClose(promise), promise, null);
        }
        return promise;
    }

    private void invokeClose(GameChannelPromise promise) {
        try {
            ((GameChannelOutboundHandler) handler()).close(this, promise);
        } catch (Throwable t) {
            notifyOutboundHandlerException(t, promise);
        }
    }

    private static boolean safeExecute(EventExecutor executor, Runnable runnable, GameChannelPromise promise, Object msg) {
        try {
            executor.execute(runnable);
            return true;
        } catch (Throwable cause) {
            try {
                promise.setFailure(cause);
            } finally {
                if (msg != null) {
                    ReferenceCountUtil.release(msg);
                }
            }
            return false;
        }
    }

}
