package com.whk.actor;

import com.whk.MessageI18n;
import com.whk.db.entity.PlayerEntity;
import com.whk.error.FastGameErrorException;
import com.whk.factory.PlayerFactory;

import com.whk.service.player.PlayerService;
import org.whk.SpringUtils;

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
        public Map<Long, Player> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 玩家登录
     *
     * @param playerId       玩家id
     * @param gateInstanceId 网关实例id
     */
    public void playerLogin(Long playerId, String gateInstanceId) {
        synchronized (playerManager) {
            if (!playerManager.playerMap.containsKey(playerId)) {
                playerManager.playerMap.put(playerId, PlayerFactory.buildPlayer(playerId, gateInstanceId));
            }
        }
    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     * @return
     */
    public Optional<Player> getPlayer(Long playerId) {
        return Optional.ofNullable(playerManager.playerMap.get(playerId));
    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     * @return
     */
    public Boolean containsPlayer(Long playerId) {
        return playerManager.playerMap.containsKey(playerId);
    }

    /**
     * 创建玩家
     *
     * @param userId
     * @param gateInstanceId 网关实例id
     */
    public boolean creatPlayer(Long userId, String gateInstanceId, Long pid) {
        var isSuccess = false;
        // 检查角色
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerOpt = playerService.findPlayerById(pid);
        if (playerOpt.isPresent()){
            throw new FastGameErrorException(MessageI18n.getMessageTuple(15));
        } else {
            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setId(pid);
            playerEntity.setCareer(1);
            playerEntity.setSex((byte) 1);
            playerEntity.setUserAccountId(userId);
            playerEntity.setLastLogin(System.currentTimeMillis());

            isSuccess = createPlayer0(gateInstanceId, playerEntity);
            playerService.create(playerEntity);
        }
        return isSuccess;
    }

    private Boolean createPlayer0(String gateInstanceId, PlayerEntity playerEntity){
        var player = PlayerFactory.createPlayer(playerEntity, gateInstanceId);
        playerLogin(player.getId(), gateInstanceId);
        return containsPlayer(playerEntity.getId());
    }

}
