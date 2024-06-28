package com.whk.net.rpc;

import com.whk.actor.PlayerMgr;
import com.whk.db.entity.PlayerEntity;
import com.whk.db.mapper.PlayerMapper;
import com.whk.rpc.model.PlayerInfo;
import com.whk.net.SendMessageHolder;
import com.whk.rpc.api.IRpcPlayerBase;
import org.springframework.data.domain.Example;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    private PlayerMapper playerMapper;

    @Resource
    public void setPlayerMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public List<PlayerInfo> getPlayers(Long userId) {
        PlayerEntity playerEntity = new PlayerEntity();
        List<PlayerInfo> re;
        var l = playerMapper.findAll(Example.of(playerEntity));
        re = l.stream().map(f -> new PlayerInfo().setCareer(f.getCareer()).setSex(f.getSex()).setLastLogin(f.getLastLogin())
                .setId(f.getId())).toList();
        return re;
    }

    @Override
    public void createPlayer(String instanceId, Long pid) {
        PlayerMgr.INSTANCE.creatPlayer(instanceId, pid);
        SendMessageHolder.INSTANCE.sendTips(pid, 18);
    }

    @Override
    public void playerLogin(long userId, String gateInstanceId, long playerId){
        PlayerMgr.INSTANCE.playerLogin(userId, gateInstanceId, playerId);
    }

    @Override
    public void test(String userName) {
        System.out.println(userName);
    }
}
