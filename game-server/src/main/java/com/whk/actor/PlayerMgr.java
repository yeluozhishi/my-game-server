package com.whk.actor;

import com.whk.message.MESSAGE_CODE;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.error.FastGameErrorException;
import com.whk.actor.build.PlayerFactory;

import com.whk.net.SendMessageHolder;
import com.whk.service.player.PlayerService;
import com.whk.SpringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
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
    public void playerLogin(String gateTopic, long playerId, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerEntityOptional = playerService.find(playerId);
        if (playerEntityOptional.isPresent()) {
            addPlayer(PlayerFactory.createPlayer(playerEntityOptional.get(), gateTopic, gateServerId));
            SendMessageHolder.INSTANCE.sendTips(MESSAGE_CODE.角色登录成功, playerId);
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
    public Player getPlayer(Long playerId) {
        return playerMap.get(playerId);
    }

    /**
     * 创建玩家
     *
     * @param pid       玩家id
     * @param gateTopic 网关
     */
    public void creatPlayer(String gateTopic, Long pid, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 检查角色
        var playerService = SpringUtils.getBean(PlayerService.class);
        var playerOpt = playerService.find(pid);
        if (playerOpt.isPresent()) {
            throw new FastGameErrorException(MESSAGE_CODE.还没有账户);
        }

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(pid);
        playerEntity.setCareer(1);
        playerEntity.setSex((byte) 1);
        playerEntity.setLastLogin(System.currentTimeMillis());

        playerEntity = playerService.create(pid, pid, playerEntity);
        addPlayer(PlayerFactory.createPlayer(playerEntity, gateTopic, gateServerId));
    }

    public PlayerActor buildPlayerActor(Player player) {
        PlayerActor actor = new PlayerActor();
        actor.setId(player.getId());
        actor.setDateServerId(player.getServerInfo().getServerId());
        actor.setGateServerId(player.getServerInfo().getGateServerId());
        actor.setGateTopic(player.getServerInfo().getGateTopic());
        actor.setAttributes(player.getAttributes());
        return actor;
    }

}
