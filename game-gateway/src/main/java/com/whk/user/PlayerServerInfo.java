package com.whk.user;

import org.whk.message.Server;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerServerInfo {

    private long playerId;

    /**
     * 游戏服id
     */
    private Server server;

    /**
     * 当前所在游戏服id
     */
    private Server presentServer;


    public PlayerServerInfo(Server server, Server presentServer, long playerId) {
        this.server = server;
        this.presentServer = presentServer;
        this.playerId = playerId;
    }
}
