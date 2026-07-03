package com.whk;

import com.whk.protobuf.message.MSGIDProto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Router {
    private static Router instance = new Router();

    private Router() {
        register();
    }

    public static Router getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Router();
        }
        return instance;
    }

    private final Map<Integer, MSGIDProto.MSGID> cmdRouter = new HashMap<>();


    public MSGIDProto.MSGID getServerType(int cmd) {
        return cmdRouter.get(cmd);
    }


    public void register() {
        
        // gate_server
        cmdRouter.put(MSGIDProto.MSGID.LoginProto_ReqLogin.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.CreatePlayerProto_CreatePlayer.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_ReqPlayerLogin.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_TestMessage.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_ReqPlayers.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.SceneProto_ReqEnterScene.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_PlayerInfos.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.TipsProto_Tips.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.LoginProto_ResLogin.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.SceneProto_ResEnterScene.getNumber(), MSGIDProto.MSGID.gate_server);
        cmdRouter.put(MSGIDProto.MSGID.CreatePlayerProto_ResCreatePlayer.getNumber(), MSGIDProto.MSGID.gate_server);
        
        // gate_server
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_ReqLevelUp.getNumber(), MSGIDProto.MSGID.game_server);
        cmdRouter.put(MSGIDProto.MSGID.PlayerInfoProto_ResLevelUp.getNumber(), MSGIDProto.MSGID.game_server);
        
        // gate_server
        cmdRouter.put(MSGIDProto.MSGID.SkillProto_ReqReleaseSkill.getNumber(), MSGIDProto.MSGID.scene_server);
        cmdRouter.put(MSGIDProto.MSGID.SceneProto_SceneMessage.getNumber(), MSGIDProto.MSGID.scene_server);
        
    }
}
