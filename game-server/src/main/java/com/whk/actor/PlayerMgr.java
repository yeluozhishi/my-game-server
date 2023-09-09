package com.whk.actor;

import com.whk.factory.PlayerFactory;

import com.whk.net.channel.GameChannel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerMgr {
    // 实例
    INSTANCE;

    private PlayerManager playerManager;

    PlayerMgr() {
        playerManager = new PlayerManager();
    }

    private class PlayerManager {
        public Map<String, Player> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 玩家登录
     *
     * @param playerId       玩家id
     * @param gateInstanceId 网关实例id
     */
    public void playerLogin(String playerId, String gateInstanceId) {
        if (!playerManager.playerMap.containsKey(playerId)) {
            synchronized (playerManager) {
                playerManager.playerMap.put(playerId, PlayerFactory.buildPlayer(playerId, new GameChannel(), gateInstanceId));
            }
        }

    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     * @return
     */
    public Optional<Player> getPlayer(String playerId) {
        return Optional.ofNullable(playerManager.playerMap.get(playerId));
    }

    /**
     * 创建玩家
     *
     * @param userName       用户名
     * @param gateInstanceId 网关实例id
     */
    public boolean creatPlayer(String userName, String gateInstanceId, String pid) {
        var isSuccess = false;
        // 检查角色
//        var user = Dbo.userAccountDao.findByUser(userName);
//        if (user.isPresent()){
//            user.get().addPlayer(createPlayer0(gateInstanceId, pid));
//            isSuccess = true;
//        } else {
//            UserAccount account = new UserAccount();
//            account.setUserName(userName);
//            account.setPlayers(List.of(createPlayer0(gateInstanceId, pid)));
//            isSuccess = true;
//        }
        return isSuccess;
    }

//    private PlayerBase createPlayer0(String gateInstanceId, String pid){
//        var player = PlayerFactory.createPlayer(pid, gateInstanceId);
//        playerLogin(player.id, gateInstanceId);
//        var p = new PlayerBase(player.id, player.kind, player.sex, System.currentTimeMillis());
//        return p;
//    }

}
