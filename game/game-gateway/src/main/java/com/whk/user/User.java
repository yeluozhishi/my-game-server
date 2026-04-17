package com.whk.user;

import com.whk.net.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class User {

    private Long userId;

    private final Session session;

    private PlayerServerInfo serverInfo;

    public User(Long userId, ChannelHandlerContext ctx, PlayerServerInfo serverInfo) {
        this.userId = userId;
        this.serverInfo = serverInfo;
        this.session = new Session(ctx);
        session.setId(userId);
    }

    public long getServerId() {
        return serverInfo.getDataServer().getId();
    }

}
