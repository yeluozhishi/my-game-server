package com.whk.net.channel;

import com.whk.net.enity.MapBeanServer;
import com.whk.net.enity.Message;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ObjectUtil;

import java.util.logging.Logger;

public abstract class AbstractGameChannelHandlerContext {

    public static Logger logger = Logger.getLogger(AbstractGameChannelHandlerContext.class.getName());

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

    private void invokeChannelInactive() {
        try {
            ((GameChannelInboundHandler) handler()).channelInactive(this);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

    private void notifyHandlerException(Throwable cause) {
        logger.warning("An exception was thrown by a user handler while handling an exceptionCaught event " + cause);
        try {
            handler().exceptionCaught(this, cause);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected AbstractGameChannelHandlerContext fireExceptionCaught(Throwable cause) {
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
                logger.warning("Failed to submit an exceptionCaught() event. " + t);
                logger.warning("The exceptionCaught() event that was failed to submit was: " + cause);
            }
        }
    }

    private void invokeExceptionCaught(final Throwable cause) {
        try {
            handler().exceptionCaught(this, cause);
        } catch (Throwable error) {
            logger.warning("An exception '{" + error + "}' [enable DEBUG level for full stacktrace] was thrown " +
                    "by a user handler's exceptionCaught() method while handling the following exception:" + cause);
        }

    }

    protected AbstractGameChannelHandlerContext fireChannelRegistered(String playerId, GameChannelPromise promise) {
        invokeChannelRegistered(findContextInbound(), playerId, promise);
        return this;
    }

    static void invokeChannelRegistered(final AbstractGameChannelHandlerContext next, String playerId, GameChannelPromise promise) {
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

    private void invokeChannelRegistered(String playerId, GameChannelPromise promise) {
        try {
            ((GameChannelInboundHandler) handler()).channelRegister(this, playerId, promise);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

    protected AbstractGameChannelHandlerContext fireChannelRead(Message msg) {
        invokeChannelRead(findContextInbound(), msg);
        return this;
    }

    static void invokeChannelRead(final AbstractGameChannelHandlerContext next, final Message msg) {
        ObjectUtil.checkNotNull(msg, "msg");
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelRead(msg);
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelRead(msg);
                }
            });
        }
    }

    private void invokeChannelRead(Message msg) {
        try {
            ((GameChannelInboundHandler) handler()).channelRead(this, msg);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }

    }

    public AbstractGameChannelHandlerContext fireChannelReadRPCRequest(MapBeanServer msg) {
        invokeChannelReadRPCRequest(findContextInbound(), msg);
        return this;
    }

    static void invokeChannelReadRPCRequest(final AbstractGameChannelHandlerContext next, final MapBeanServer msg) {
        ObjectUtil.checkNotNull(msg, "msg");
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelReadRPCRequest(msg);
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelReadRPCRequest(msg);
                }
            });
        }
    }

    private void invokeChannelReadRPCRequest(MapBeanServer msg) {
        try {
            ((GameChannelInboundHandler) handler()).channelReadRPCRequest(this, msg);
        } catch (Throwable t) {
            notifyHandlerException(t);
        }
    }

}
