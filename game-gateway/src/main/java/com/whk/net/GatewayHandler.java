package com.whk.net;

import com.whk.user.UserMgr;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.logging.Logger;

public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(GatewayHandler.class.getName());

    private volatile Channel channel;
    private SocketAddress remoteAddr;
    private Boolean isConnected = false;


    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
        logger.info(this.remoteAddr + "channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        isConnected = false;
        logger.warning("gate way channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {




        ResponseTest result = new ResponseTest();
        MessageTest request = (MessageTest)msg;
        System.out.println(request.getCommand() + "," + request.getOp());
        result.setCommand(request.getCommand());
        result.setUser(request.getUserNames().get(0));
        result.setRe("gotta something");
        ctx.writeAndFlush(result);
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    /**
     * 协议转发
     * @param msg
     */
    private void transmit(Object msg){
        var message = (Message) msg;
        if (message.getComeFromClient()){
            // 来自客户端，转发给服务器
            var user = UserMgr.INSTANCE.getUser(message.getUserNames().get(0));
            if (user.isPresent()){
                if (user.get().getToServerId() != 0){
                    // 跳转的服务器

                } else {
                    // 本服


                }


            }


        } else {
            // 来自服务器，转发给客户端
            message.getUserNames().forEach(f -> {
                var user = UserMgr.INSTANCE.getUser(f);
                if (user.isPresent()){
                    user.get().sendToClientMessage(msg);
                }
            });
        }
    }

}
