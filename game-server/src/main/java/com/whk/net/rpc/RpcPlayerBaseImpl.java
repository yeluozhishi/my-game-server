package com.whk.net.rpc;

import com.whk.actor.PlayerMgr;
import com.whk.mongodb.Dbo;
import com.whk.net.SendMessageHolder;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.rpc.api.IRpcPlayerBase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    @Override
    public List<MapBean> getPlayers(String userName) {
        var user = Dbo.userAccountDao.findByUser(userName);
        if (user.isPresent()){
            var players = user.get().getPlayers();
            var list = players.stream().map(player -> {
                var m = new MapBean();
                m.putAll(Map.of("id", player.id, "career", player.kind, "sex", player.sex, "lastLogin", player.lastLogin));
                return m;
            }).collect(Collectors.toList());
            return list;
        }
        return null;
    }

    @Override
    public Boolean createPlayer(String userName, String instanceId, String pid) {
        var isSuccess = PlayerMgr.INSTANCE.creatPlayer(userName, instanceId, pid);
        var player = PlayerMgr.INSTANCE.getPlayer(pid);
        if (player.isPresent()){
            SendMessageHolder.INSTANCE.sendMessage(new Message(0x0002, pid, new MapBean(Map.of("msg", "登录成功"))));
        }
        return isSuccess;
    }

    @Override
    public void test(String userName) {
        var players = getPlayers(userName);
        System.out.println(players);
    }
}
