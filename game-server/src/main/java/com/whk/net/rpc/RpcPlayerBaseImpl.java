package com.whk.net.rpc;

import com.whk.MessageI18n;
import com.whk.actor.PlayerMgr;
import com.whk.db.entity.PlayerEntity;
import com.whk.db.mapper.PlayerMapper;
import com.whk.rpc.model.PlayerInfo;
import com.whk.net.SendMessageHolder;
import com.whk.rpc.api.IRpcPlayerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.whk.TipsConvert;
import org.whk.protobuf.message.MessageOuterClass;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
public class RpcPlayerBaseImpl implements IRpcPlayerBase {

    private PlayerMapper playerMapper;

    @Autowired
    public void setPlayerMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public List<PlayerInfo> getPlayers(Long userId) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUserAccountId(userId);
        List<PlayerInfo> re = new LinkedList<>();
        var l = playerMapper.findAll(Example.of(playerEntity));
        if (Objects.nonNull(l)){
            re = l.stream().map(f -> new PlayerInfo().setCareer(f.getCareer()).setSex(f.getSex()).setLastLogin(f.getLastLogin())
            .setId(f.getId()).setUserAccountId(f.getUserAccountId())).toList();
        }
        return re;
    }

    @Override
    public Boolean createPlayer(Long userId, String instanceId, Long pid) throws IOException {
        var isSuccess = PlayerMgr.INSTANCE.creatPlayer(userId, instanceId, pid);
        MessageOuterClass.Message.Builder builder = MessageOuterClass.Message.newBuilder()
                .setCommand(0x0002).setTips(TipsConvert.convert(MessageI18n.getMessageTuple(16)));
        SendMessageHolder.INSTANCE.sendMessage(builder.build(), pid);
        return isSuccess;
    }

    @Override
    public void test(String userName) {
        System.out.println(userName);
    }
}
