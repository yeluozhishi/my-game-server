package com.whk.actor;

import com.whk.net.channel.GameChannel;

import java.util.Map;
import java.util.Optional;
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

    public void playerLogin(String playerId, String gateInstanceId){
        if (!playerManager.playerMap.containsKey(playerId)){
            var player = new Player(playerId , new GameChannel(), gateInstanceId, true);
            player.init();
            synchronized (playerManager) {
                playerManager.playerMap.put(playerId, player);
            }
        }

    }


    public Optional<Player> getPlayer(String playerId){
        return Optional.ofNullable(playerManager.playerMap.get(playerId));
    }
}
