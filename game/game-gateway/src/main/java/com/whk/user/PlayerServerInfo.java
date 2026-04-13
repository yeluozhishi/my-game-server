package com.whk.user;

import com.whk.message.Server;
import com.whk.Router;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PlayerServerInfo {

    // 当前玩家id
    private long playerId;

    // 角色id列表
    private Set<Long> playerIds = new HashSet<>();

    /**
     * 数据服id
     */
    private Server dataServer;

    /**
     * 当前所在游戏服id
     */
    private Server sceneServer;

    @Setter
    private String topic;

    public PlayerServerInfo(Server dataServer) {
        this.dataServer = dataServer;
        this.sceneServer = dataServer;
    }

    public boolean setPlayerId(long playerId) {
        if (playerIds.contains(playerId)) {
            this.playerId = playerId;
            return true;
        }
        return false;
    }

    public boolean inScene() {
        return sceneServer.getId() != dataServer.getId();
    }

}
