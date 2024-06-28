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

    private final PlayerManager playerManager;

    PlayerMgr() {
        playerManager = new PlayerManager();
    }

    private static class PlayerManager {
        private final Map<Long, Player> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 玩家登录
     *
     * @param playerId 玩家id
     */
    public void playerLogin(long userId, String gateInstanceId, long playerId){
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerEntityOptional = playerService.findPlayerById(playerId);
        playerEntityOptional
                .ifPresentOrElse(playerEntity -> playerLogin(gateInstanceId, playerEntity),
                        .sendMsg(userId, ));
    }


    public void addPlayer(Player player) {
        playerManager.playerMap.put(player.getId(), player);
    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     */
    public Optional<Player> getPlayer(Long playerId) {
        return Optional.ofNullable(playerManager.playerMap.get(playerId));
    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     */
    public Boolean containsPlayer(Long playerId) {
        return playerManager.playerMap.containsKey(playerId);
    }

    /**
     * 创建玩家
     *
     * @param pid         玩家id
     * @param gateInstanceId 网关实例id
     */
    public void creatPlayer(String gateInstanceId, Long pid) {
        // 检查角色
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerOpt = playerService.findPlayerById(pid);
        if (playerOpt.isPresent()) {
            throw new FastGameErrorException(MessageI18n.getMessageTuple(15));
        } else {
            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setId(pid);
            playerEntity.setCareer(1);
            playerEntity.setSex((byte) 1);
            playerEntity.setLastLogin(System.currentTimeMillis());

            playerLogin(gateInstanceId, playerEntity);
            playerService.create(playerEntity);
        }
    }


    private void playerLogin(String gateInstanceId, PlayerEntity playerEntity) {
        var player = PlayerFactory.createPlayer(playerEntity, gateInstanceId);
        addPlayer(player);
        player.init();
    }



}
