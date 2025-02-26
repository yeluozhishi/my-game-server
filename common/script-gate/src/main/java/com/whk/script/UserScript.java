package com.whk.script;

import com.whk.message.Server;
import com.whk.protobuf.message.SceneProto;
import com.whk.server.GateServerManager;
import com.whk.user.UserMgr;
import script.annotation.Script;

import java.util.Optional;

@Script
public class UserScript implements IUserScript {

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        Optional<Server> server = GateServerManager.getInstance().getServer(serverId);
        if (server.isPresent()) {
            user.getServerInfo().setSceneServer(server.get());
            SceneProto.ResEnterScene.Builder builder = SceneProto.ResEnterScene.newBuilder();
            builder.setDesc("进入场景:" + server.get().toString());
            user.sendToClientMessage(SceneProto.ResEnterScene.class, builder.build().toByteString());
        } else {
            user.sendTips(23);
        }
    }
}
