package com.whk.net.rpc;

import com.whk.actor.PlayerMgr;
import com.whk.db.repository.SysUserMapper;
import com.whk.net.SendMessageHolder;
import com.whk.rpc.api.IRpcPlayerBase;
import org.whk.message.MapBean;
import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.TipsOuterClass;

import javax.annotation.Resource;
import java.io.IOException;
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
    public Boolean createPlayer(Long userId, String userName, String instanceId, Long pid) throws IOException {
        var isSuccess = PlayerMgr.INSTANCE.creatPlayer(userId, userName, instanceId, pid);
        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder()
                .setCommand(0x0002).setPlayerId(pid);
        var tips = TipsOuterClass.Tips.newBuilder().setMsg("登录成功");
        builder.setTips(tips);
        SendMessageHolder.INSTANCE.sendMessage(builder.build());
        return isSuccess;
    }

    @Override
    public void test(String userName) {
        var players = getPlayers(userName);
        System.out.println(players);
    }
}
