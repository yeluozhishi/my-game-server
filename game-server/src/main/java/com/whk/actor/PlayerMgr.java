package com.whk.actor;

import com.whk.actor.component.BasicInfo;
import com.whk.message.MESSAGE_CODE;
import com.whk.db.entity.PlayerEntity;
import com.whk.actor.build.PlayerFactory;

import com.whk.message.MapBean;
import com.whk.message.MessageI18n;
import com.whk.service.player.PlayerService;
import com.whk.SpringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
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
    public MapBean playerLogin(String gateTopic, long playerId, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerService = SpringUtils.getBean(PlayerService.class);
        var basicInfo = playerService.find(playerId);
        if (Objects.nonNull(basicInfo)) {
            addPlayer(PlayerFactory.createPlayer(basicInfo, gateTopic, gateServerId));
            return MessageI18n.getMessageMapBean(MESSAGE_CODE.创建角色成功);
        } else {
            return MessageI18n.getMessageMapBean(MESSAGE_CODE.角色登录失败);
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
     * @param gateTopic 网关
     * @param pid       玩家id
     * @param name
     * @return
     */
    public MapBean creatPlayer(String gateTopic, Long pid, int gateServerId, String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 检查角色
        var playerService = SpringUtils.getBean(PlayerService.class);
        var basicInfo = playerService.find(pid);
        if (Objects.nonNull(basicInfo)) {
            return MessageI18n.getMessageMapBean(MESSAGE_CODE.已有角色);
        }

        basicInfo = new BasicInfo();
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(pid);
        playerEntity.setCareer(1);
        playerEntity.setSex((byte) 1);
        playerEntity.setName(name);
        playerEntity.setLastLogin(System.currentTimeMillis());
        basicInfo.setEntity(playerEntity);

        playerService.create(pid, basicInfo);
        addPlayer(PlayerFactory.createPlayer(basicInfo, gateTopic, gateServerId));
        return MessageI18n.getMessageMapBean(MESSAGE_CODE.创建角色成功);
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
