package com.whk.script;

import com.whk.message.MESSAGE_CODE;
import com.whk.message.Server;
import com.whk.protobuf.message.SceneProto;
import com.whk.server.GateServerManager;
import com.whk.user.UserMgr;
import script.annotation.Script;

import java.util.Objects;
import java.util.Optional;

@Script
public class UserScript implements IUserScript {

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        Server server = GateServerManager.getInstance().getServer(serverId);
        if (Objects.nonNull(server)) {
            user.getServerInfo().setSceneServer(server);
            SceneProto.ResEnterScene.Builder builder = SceneProto.ResEnterScene.newBuilder();
            builder.setDesc("进入场景:" + server);
            user.sendToClientMessage(SceneProto.ResEnterScene.class, builder.build().toByteString());
        } else {
            user.sendTips(MESSAGE_CODE.升级失败);
        }
    }
}
