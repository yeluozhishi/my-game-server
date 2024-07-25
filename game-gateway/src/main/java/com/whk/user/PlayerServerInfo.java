package com.whk.user;

import com.whk.message.Server;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PlayerServerInfo {

    // 当前玩家id
    private long playerId;

    // 角色id列表
    private Set<Long> playerIds = new HashSet<>();

    /**
     * 游戏服id
     */
    private Server server;

    /**
     * 当前所在游戏服id
     */
    private Server presentServer;

    @Setter
    private String topic;

    public PlayerServerInfo(Server server, Server presentServer) {
        this.server = server;
        this.presentServer = presentServer;
    }

    public boolean setPlayerId(long playerId) {
        if (!playerIds.isEmpty() && playerIds.contains(playerId)) {
            this.playerId = playerId;
            return true;
        }
        return false;
    }

    public String getPresentServerTopic(){
        return "%s-%d-%d".formatted(topic, presentServer.getServerZone(), presentServer.getId());
    }

}
