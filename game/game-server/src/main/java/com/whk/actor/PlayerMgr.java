package com.whk.actor;

import com.whk.SpringUtils;
import com.whk.actor.build.PlayerFactory;
import com.whk.actor.component.BasicInfo;
import com.whk.actor.component.PlayerModule;
import com.whk.db.entity.PlayerEntity;
import com.whk.db.entity.UserPlayerEntity;
import com.whk.match.id.IDConst;
import com.whk.match.id.UIDUtil;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MapBean;
import com.whk.message.MessageI18n;
import com.whk.module.LevelModule;
import com.whk.net.MessageUtil;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.service.player.PlayerModuleService;
import com.whk.service.player.PlayerService;
import com.whk.service.user.UserPlayerService;
import org.springframework.data.domain.Example;

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
     * @param userId
     */
    public void playerLogin(String gateTopic, long playerId, int gateServerId, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerService = SpringUtils.getBean(PlayerService.class);
        var basicInfo = playerService.find(playerId);
        if (Objects.nonNull(basicInfo)) {
            Player player = PlayerFactory.createPlayer(basicInfo, gateTopic, gateServerId);
            addPlayer(player);

            PlayerModule playerModule = SpringUtils.getBean(PlayerModuleService.class).find(player.getId());
            if (Objects.isNull(playerModule)) return;
            LevelModule levelModule = playerModule.getModule(LevelModule.class);
            PlayerInfoProto.ResPlayerLogin.Builder builder = PlayerInfoProto.ResPlayerLogin.newBuilder();
            PlayerInfoProto.PlayerSimpleInfo.Builder simpleInfo = PlayerInfoProto.PlayerSimpleInfo.newBuilder();
            simpleInfo.setPlayerId(player.getId()).setName(player.getBasicInfo().getName()).setLevel(levelModule.getLevel())
                    .setExp(levelModule.getExp()).setCareer(player.getBasicInfo().getCareer()).setSex(player.getBasicInfo().getSex())
                    .setOnline(true).setCreateTime(player.getBasicInfo().getCreateTime()).setServerId(player.getServerInfo().getServerId())
                    .setMapId(player.getTemporary().getMapId());
            builder.setInfo(simpleInfo);
            MessageUtil.getInstance().sendMessage(builder.build(), playerId);
        } else {
            RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId)
                    .resPlayerLogin(userId, MessageI18n.getMessageMapBean(MESSAGE_CODE.角色登录失败));
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
     */
    public void creatPlayer(String gateTopic, int gateServerId, CreatePlayerProto.CreatePlayer message) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 检查角色
        var userPlayerService = SpringUtils.getBean(UserPlayerService.class);
        UserPlayerEntity userPlayerEntity = new UserPlayerEntity();
        userPlayerEntity.setUserId(message.getUserId());
        var userPlayers = userPlayerService.findByExample(message.getUserId(), Example.of(userPlayerEntity));
        if (userPlayers.size() >= 4) {
            RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId)
                    .resCreatePlayerFailure(MessageI18n.getMessageMapBean(MESSAGE_CODE.角色数量已满), message.getUserId());
            return;
        }

        var playerService = SpringUtils.getBean(PlayerService.class);

        long playerId = UIDUtil.getId(IDConst.ROLE);
        if (playerService.exists(playerId)) {
            RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId)
                    .resCreatePlayerFailure(MessageI18n.getMessageMapBean(MESSAGE_CODE.创建角色失败), message.getUserId());
            return;
        }

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(playerId);
        playerEntity.setCareer(message.getCareer());
        playerEntity.setSex(message.getSex());
        playerEntity.setName(message.getName());
        playerEntity.setLastLogin(System.currentTimeMillis());
        BasicInfo basicInfo = playerService.updateImmediately(message.getUserId(), playerEntity);

        addPlayer(PlayerFactory.createPlayer(basicInfo, gateTopic, gateServerId));

        userPlayerEntity.setPlayerId(playerId);
        userPlayerService.updateImmediately(message.getUserId(), userPlayerEntity);

        MapBean bean = MessageI18n.getMessageMapBean(MESSAGE_CODE.创建角色成功);
        bean.put("playerId", playerEntity.getId());
        bean.put("career", playerEntity.getCareer());
        bean.put("sex", playerEntity.getSex());
        bean.put("serverId", message.getServerId());
        RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId).resCreatePlayer(bean, message.getUserId());
    }

    public PlayerActor buildPlayerActor(Player player) {
        PlayerActor actor = new PlayerActor();
        actor.setId(player.getId());
        actor.setDateServerId(player.getServerInfo().getServerId());
        actor.setGateServerId(player.getServerInfo().getGateServerId());
        actor.setGateTopic(player.getServerInfo().getGateTopic());
        actor.setAttributes(player.getTemporary().getAttributes());
        return actor;
    }

}
