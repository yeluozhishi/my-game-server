package com.whk.net.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.gamedb.mapper.PlayerMapper;
import com.whk.net.rpc.api.IRpcPlayerBase;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.service.player.PlayerService;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    private final Logger logger = Logger.getLogger(RpcPlayerBaseImpl.class.getName());

    @Override
    public ListWrapper<PlayerInfo> getPlayers(ListWrapper<Long> playerIds) {
        var service = SpringUtils.getBean(PlayerService.class);
        var l = service.findAllByIds(playerIds.immutableList());
        return new ListWrapper<>(BeanUtil.copyToList(l, PlayerInfo.class));
    }

    @Override
    public void createPlayer(String gateTopic, Long pid) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerMgr.INSTANCE.creatPlayer(gateTopic, pid);
    }

    @Override
    public void playerLogin(String gateTopic, long playerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.info("角色登录完成");
        PlayerMgr.INSTANCE.playerLogin(gateTopic, playerId);
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
