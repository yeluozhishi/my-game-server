package com.whk.actor;

import com.whk.net.channel.GameChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerMgr {
    // 实例
    INSTANCE;

    private PlayerManager playerManager;

    PlayerMgr(){
        playerManager = new PlayerManager();
    }

    private class PlayerManager{
        public Map<String, Player> playerMap = new ConcurrentHashMap<>();
    }

    public void playerLogin(String playerId){
        if (!playerManager.playerMap.containsKey(playerId)){
            var player = new Player(playerId , new GameChannel(), true);
            player.init();
            synchronized (playerManager) {
                playerManager.playerMap.put(playerId, player);
            }
        }



    }

}
