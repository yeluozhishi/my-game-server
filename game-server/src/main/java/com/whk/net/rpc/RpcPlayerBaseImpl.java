package com.whk.net.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.gamedb.mapper.PlayerMapper;
import com.whk.net.rpc.api.IRpcPlayerBase;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;

import java.util.logging.Logger;

/**
 * @author Administrator
 */
public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    private final Logger logger = Logger.getLogger(RpcPlayerBaseImpl.class.getName());

    @Override
    public ListWrapper<PlayerInfo> getPlayers(ListWrapper<Long> playerIds) {
        var playerMapper = SpringUtils.getBean(PlayerMapper.class);
        var l = playerMapper.findAllById(playerIds.immutableList());
        return new ListWrapper<>(BeanUtil.copyToList(l, PlayerInfo.class));
    }

    @Override
    public void createPlayer(String instanceId, Long pid) {
        PlayerMgr.INSTANCE.creatPlayer(instanceId, pid);
    }

    @Override
    public boolean playerLogin(String gateInstanceId, long playerId){
        logger.info("角色登录完成");
        return PlayerMgr.INSTANCE.playerLogin(gateInstanceId, playerId);
    }

    @Override
    public void test(String userName) {
        System.out.println(userName);
    }

    @Override
    public String testString(String context) {
        return "%s repeat".formatted(context);
    }
}
