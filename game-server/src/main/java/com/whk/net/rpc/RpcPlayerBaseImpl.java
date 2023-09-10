package com.whk.net.rpc;

import com.whk.actor.PlayerMgr;
import com.whk.db.repository.SysUserMapper;
import com.whk.net.SendMessageHolder;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.rpc.api.IRpcPlayerBase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    private SysUserMapper sysUserMapper;

    @Resource
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public List<MapBean> getPlayers(String userName) {

        var user = sysUserMapper.findById(1L);
//        if (user.isPresent()){
//            var players = user.get().getPlayers();
//            var list = players.stream().map(player -> {
//                var m = new MapBean();
//                m.putAll(Map.of("id", player.id, "career", player.kind, "sex", player.sex, "lastLogin", player.lastLogin));
//                return m;
//            }).collect(Collectors.toList());
//            return list;
//        }
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
