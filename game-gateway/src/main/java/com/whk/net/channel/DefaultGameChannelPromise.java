package com.whk.net.channel;

import io.netty.channel.Channel;
import io.netty.util.concurrent.*;


public class DefaultGameChannelPromise extends DefaultPromise<Void> implements GameChannelPromise{

    private final GameChannel channel;

    /**
     * Creates a new instance.
     *
     * @param channel the {@link Channel} associated with this future
     */
    public DefaultGameChannelPromise(GameChannel channel) {
        this.channel = channel;
    }

    /**
     * Creates a new instance.
     *
     * @param channel the {@link Channel} associated with this future
     */
    public DefaultGameChannelPromise(GameChannel channel, EventExecutor executor) {
        super(executor);
        this.channel = channel;
    }


    @Override
    protected EventExecutor executor() {
        EventExecutor e = super.executor();
        if (e == null) {
            return channel().executor();
        } else {
            return e;
        }
    }

    @Override
    public GameChannel channel() {
        return channel;
    }

    @Override
    public GameChannelPromise setSuccess(Void result) {
        super.setSuccess(result);
        return this;
    }

    @Override
    public GameChannelPromise setSuccess() {
        return setSuccess(null);
    }

    @Override
    public boolean trySuccess() {
        return trySuccess(null);
    }

    @Override
    public boolean trySuccess(Void result) {
        return super.trySuccess(result);
    }

    @Override
    public GameChannelPromise setFailure(Throwable cause) {
        super.setFailure(cause);
        return this;
    }

    @Override
    public GameChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.addListener(listener);
        return this;
    }

    @Override
    public GameChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.addListeners(listeners);
        return this;
    }

    @Override
    public GameChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        super.removeListener(listener);
        return this;
    }

    @Override
    public GameChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) {
        super.removeListeners(listeners);
        return this;
    }

    @Override
    public GameChannelPromise sync() throws InterruptedException {
        super.sync();
        return this;
    }

    @Override
    public GameChannelPromise syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    @Override
    public GameChannelPromise await() throws InterruptedException {
        super.await();
        return this;
    }

    @Override
    public GameChannelPromise awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    @Override
    protected void checkDeadLock() {
        super.checkDeadLock();
    }
}
