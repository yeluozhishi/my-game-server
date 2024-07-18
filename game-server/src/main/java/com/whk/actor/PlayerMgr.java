package com.whk.actor;

import com.whk.MessageI18n;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.error.FastGameErrorException;
import com.whk.actor.build.PlayerFactory;

import com.whk.service.player.PlayerService;
import com.whk.SpringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerMgr {
    // 实例
    INSTANCE;

    private final Map<Long, Player> playerMap = new ConcurrentHashMap<>();

    public void init() {
        PlayerFactory.register();
    }

    /**
     * 玩家登录
     *
     * @param playerId 玩家id
     */
    public void playerLogin(String gateTopic, long playerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerEntityOptional = playerService.find(playerId);
        if (playerEntityOptional.isPresent()) {
            var player = PlayerFactory.createPlayer(playerEntityOptional.get(), gateTopic, false);
            addPlayer(player);
        }
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
     * @param pid       玩家id
     * @param gateTopic 网关
     */
    public void creatPlayer(String gateTopic, Long pid) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 检查角色
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerOpt = playerService.find(pid);
        if (playerOpt.isPresent()) {
            throw new FastGameErrorException(MessageI18n.getMessageTuple(15));
        }

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(pid);
        playerEntity.setCareer(1);
        playerEntity.setSex((byte) 1);
        playerEntity.setLastLogin(System.currentTimeMillis());

        playerEntity = playerService.create(playerEntity);
        var player = PlayerFactory.createPlayer(playerEntity, gateTopic, true);
        addPlayer(player);
    }

}
