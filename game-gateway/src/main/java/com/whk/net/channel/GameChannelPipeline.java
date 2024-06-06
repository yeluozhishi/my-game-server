package com.whk.net.channel;

import com.whk.net.concurrent.GameEventExecutorGroup;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import org.whk.protobuf.message.MessageWrapperProto;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

public class GameChannelPipeline {
    private Logger logger = Logger.getLogger(GameChannelPipeline.class.getName());

    private static final String HEAD_NAME = generateName0(HeadContext.class);

    private static final String TAIL_NAME = generateName0(TailContext.class);

    private static final FastThreadLocal<Map<Class<?>, String>> nameCaches = new FastThreadLocal<>() {
        @Override
        protected Map<Class<?>, String> initialValue() {
            return new WeakHashMap<>();
        }
    };

    final AbstractGameChannelHandlerContext head;
    final AbstractGameChannelHandlerContext tail;

    private final GameChannel channel;

    private Map<EventExecutorGroup, EventExecutor> childExecutors;


    protected GameChannelPipeline(GameChannel channel) {
        this.channel = ObjectUtil.checkNotNull(channel, "channel");

        tail = new TailContext(this);
        head = new HeadContext(this);

        head.next = tail;
        tail.prev = head;
    }

    public final GameChannel gameChannel() {
        return channel;
    }

    private static String generateName0(Class<?> handlerType) {
        return StringUtil.simpleClassName(handlerType) + "#0";
    }

    public final GameChannelPipeline addLast(boolean singleEventExecutorPerGroup, String name, GameChannelHandler handler) {
        return addLast(null, singleEventExecutorPerGroup, name, handler);
    }

    public final GameChannelPipeline addLast(GameEventExecutorGroup group, boolean singleEventExecutorPerGroup, String name, GameChannelHandler handler) {
        final AbstractGameChannelHandlerContext newCtx;
        synchronized (this) {
            newCtx = newContext(group, singleEventExecutorPerGroup, filterName(name, handler), handler);
            addLast0(newCtx);
        }
        return this;
    }

    public final GameChannelPipeline addLast(GameChannelHandler... handlers) {
        return addLast(null, false, handlers);
    }

    public final GameChannelPipeline addLast(GameEventExecutorGroup executor, boolean singleEventExecutorPerGroup, GameChannelHandler... handlers) {
        if (handlers == null) {
            throw new NullPointerException("handlers");
        }

        for (GameChannelHandler h : handlers) {
            if (h == null) {
                break;
            }
            addLast(executor, false, null, h);
        }

        return this;
    }


    private void addLast0(AbstractGameChannelHandlerContext newCtx) {
        AbstractGameChannelHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }

    private String filterName(String name, GameChannelHandler handler) {
        if (name == null) {
            return generateName(handler);
        }
        checkDuplicateName(name);
        return name;
    }

    private void checkDuplicateName(String name) {
        if (context0(name) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private AbstractGameChannelHandlerContext context0(String name) {
        AbstractGameChannelHandlerContext context = head.next;
        while (context != tail) {
            if (context.getName().equals(name)) {
                return context;
            }
            context = context.next;
        }
        return null;
    }

    private String generateName(GameChannelHandler handler) {
        Map<Class<?>, String> cache = nameCaches.get();
        Class<?> handlerType = handler.getClass();
        String name = cache.get(handlerType);
        if (name == null) {
            name = generateName0(handlerType);
            cache.put(handlerType, name);
        }

        // It's not very likely for a user to put more than one handler of the same type, but make sure to
        // avoid
        // any name conflicts. Note that we don't cache the names generated here.
        if (context0(name) != null) {
            String baseName = name.substring(0, name.length() - 1); // Strip the trailing '0'.
            for (int i = 1;; i++) {
                String newName = baseName + i;
                if (context0(newName) == null) {
                    name = newName;
                    break;
                }
            }
        }
        return name;
    }

    /**
     *
     * <p>
     * Description: 创建一个实例
     * </p>
     *
     * @param group
     * @param singleEventExecutorPerGroup
     *        如果为true，那么多个不同的Handler如果使用同一个GameEventExecutorGroup中选择EventExecutor，在调用handler里面的方法时，都是使用的同
     *        一个EventExecutor;
     * @param name
     * @param handler
     * @return
     * @author wgs
     * @date 2019年5月25日 下午6:42:49
     *
     */
    private AbstractGameChannelHandlerContext newContext(GameEventExecutorGroup group, boolean singleEventExecutorPerGroup, String name, GameChannelHandler handler) {
        return new DefaultGameChannelHandlerContext(this, childExecutor(group, singleEventExecutorPerGroup), name, handler);
    }

    private EventExecutor childExecutor(GameEventExecutorGroup group, boolean singleEventExecutorPerGroup) {
        if (group == null) {
            return null;
        }

        if (!singleEventExecutorPerGroup) {
            return group.next();
        }
        Map<EventExecutorGroup, EventExecutor> childExecutors = this.childExecutors;
        if (childExecutors == null) {
            // Use size of 4 as most people only use one extra EventExecutor.
            childExecutors = this.childExecutors = new IdentityHashMap<EventExecutorGroup, EventExecutor>(4);
        }
        // Pin one of the child executors once and remember it so that the same child executor
        // is used to fire events for the same channel.
        EventExecutor childExecutor = childExecutors.get(group);
        if (childExecutor == null) {
            childExecutor = group.next();
            childExecutors.put(group, childExecutor);
        }
        return childExecutor;
    }

    /**
     * Called once a message hit the end of the {@link ChannelPipeline} without been handled by the user
     * in {@link ChannelInboundHandler#channelRead(ChannelHandlerContext, Object)}. This method is
     * responsible to call {@link ReferenceCountUtil#release(Object)} on the given msg at some point.
     */
    protected void onUnhandledInboundMessage(Object msg) {
        try {
            logger.info("Discarded inbound message {" + msg + "} that reached at the tail of the pipeline. " + "Please check your pipeline configuration.");
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * Called once a {@link Throwable} hit the end of the {@link ChannelPipeline} without been handled
     * by the user in {@link ChannelHandler(ChannelHandlerContext, Throwable)}.
     */
    protected void onUnhandledInboundException(Throwable cause) {
        try {
            logger.warning("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. " + "It usually means the last handler in the pipeline did not handle the exception." + cause);
        } finally {
            ReferenceCountUtil.release(cause);
        }
    }

    public final GameChannelPipeline fireChannelRegistered(Long playerId, GameChannelPromise promise) {
        AbstractGameChannelHandlerContext.invokeChannelRegistered(head, playerId, promise);
        return this;
    }

    public final GameChannelPipeline fireChannelInactive() {
        AbstractGameChannelHandlerContext.invokeChannelInactive(head);
        return this;
    }

    public final GameChannelPipeline fireChannelRead(MessageWrapperProto.MessageWrapper msg) {
        AbstractGameChannelHandlerContext.invokeChannelRead(head, msg);
        return this;
    }

    final class HeadContext extends AbstractGameChannelHandlerContext
            implements GameChannelOutboundHandler, GameChannelInboundHandler {

        HeadContext(GameChannelPipeline pipeline){
            super(false, true, pipeline, null, HEAD_NAME);
        }

        @Override
        public GameChannelHandler handler() {
            return this;
        }

        @Override
        public void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
        }

        @Override
        public void channelRegister(AbstractGameChannelHandlerContext ctx, Long playerId, GameChannelPromise promise) {
            ctx.fireChannelRegistered(playerId, promise);
        }

        @Override
        public void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelInactive();
        }

        @Override
        public void channelRead(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg) throws Exception {
            ctx.fireChannelRead(msg);
        }

        @Override
        public void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg, GameChannelPromise promise) throws Exception {
            channel.sendToServerMessage(msg);
        }

        @Override
        public void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise) {
            channel.unsafeClose();
        }
    }

    private class TailContext extends AbstractGameChannelHandlerContext
            implements GameChannelInboundHandler  {
        public TailContext(GameChannelPipeline pipeline) {
            super(true, false, pipeline, null, TAIL_NAME);
        }

        @Override
        public GameChannelHandler handler() {
            return this;
        }

        @Override
        public void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception {
            onUnhandledInboundException(cause);
        }

        @Override
        public void channelRegister(AbstractGameChannelHandlerContext ctx, Long playerId, GameChannelPromise promise) {
            promise.setSuccess();
            logger.info("注册事件未处理");
        }

        @Override
        public void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelInactive();
        }

        @Override
        public void channelRead(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg) throws Exception {
            onUnhandledInboundMessage(msg);
        }

    }

}
