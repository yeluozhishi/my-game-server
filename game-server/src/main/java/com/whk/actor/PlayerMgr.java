package com.whk.actor;

import com.whk.MessageI18n;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.error.FastGameErrorException;
import com.whk.actor.build.PlayerBuilder;

import com.whk.service.player.PlayerService;
import com.whk.SpringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerMgr {
    // 实例
    INSTANCE;

    private final Map<Long, Player> playerMap = new ConcurrentHashMap<>();



    /**
     * 玩家登录
     *
     * @param playerId 玩家id
     */
    public boolean playerLogin(String gateInstanceId, long playerId){
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerEntityOptional = playerService.findPlayerById(playerId);
        if (playerEntityOptional.isPresent()){
            playerLogin(gateInstanceId, playerEntityOptional.get());
            return true;
        }
        return false;
    }


    public void addPlayer(Player player) {
        playerMap.put(player.getId(), player);
    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     */
    public Optional<Player> getPlayer(Long playerId) {
        return Optional.ofNullable(playerMap.get(playerId));
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
        var player = PlayerBuilder.createPlayer(playerEntity, gateInstanceId);
        addPlayer(player);
        player.init();
    }



}
