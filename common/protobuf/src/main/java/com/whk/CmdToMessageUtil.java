package com.whk;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.whk.protobuf.message.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CmdToMessageUtil {

    private final Map<Integer, Message> cmdToMessageMap = new HashMap<>();

    private final Map<Class<?>, Integer> messageToCmdMap = new HashMap<>();

    private static CmdToMessageUtil instance = new CmdToMessageUtil();

    private CmdToMessageUtil() {
        setCmdToMessageMap();
        setMessageToCmdMap();
    }

    public static CmdToMessageUtil getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CmdToMessageUtil();
        }
        return instance;
    }

    public Message parsePayload(MessageProto.Message message) throws InvalidProtocolBufferException {
        Message prototype = cmdToMessageMap.get(message.getCommand());
        if (prototype == null) {
            throw new IllegalArgumentException("未知协议号: %d".formatted(message.getCommand()));
        }
        return prototype.getParserForType().parseFrom(message.getPayload());
    }

    public int getCmd(Class<?> c) {
        return messageToCmdMap.getOrDefault(c, -1);
    }


    public void setCmdToMessageMap() {
        cmdToMessageMap.put(MSGIDProto.MSGID.LoginProto_LoginReq.getNumber(), LoginProto.LoginReq.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.CreatePlayerProto_CreatePlayer.getNumber(), CreatePlayerProto.CreatePlayer.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.PlayerInfoProto_ReqPlayerLogin.getNumber(), PlayerInfoProto.ReqPlayerLogin.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.PlayerInfoProto_TestMessage.getNumber(), PlayerInfoProto.TestMessage.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.PlayerInfoProto_ReqPlayers.getNumber(), PlayerInfoProto.ReqPlayers.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.PlayerInfoProto_PlayerInfos.getNumber(), PlayerInfoProto.PlayerInfos.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.TipsProto_Tips.getNumber(), TipsProto.Tips.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.LoginProto_LoginRes.getNumber(), LoginProto.LoginRes.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.SceneProto_ResEnterScene.getNumber(), SceneProto.ResEnterScene.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.PlayerInfoProto_ReqLevelUp.getNumber(), PlayerInfoProto.ReqLevelUp.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.SkillProto_ReqReleaseSkill.getNumber(), SkillProto.ReqReleaseSkill.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.SceneProto_SceneMessage.getNumber(), SceneProto.SceneMessage.getDefaultInstance());
        cmdToMessageMap.put(MSGIDProto.MSGID.SceneProto_ReqEnterScene.getNumber(), SceneProto.ReqEnterScene.getDefaultInstance());
        
    }

    public void setMessageToCmdMap() {
        messageToCmdMap.put(LoginProto.LoginReq.class, MSGIDProto.MSGID.LoginProto_LoginReq.getNumber());
        messageToCmdMap.put(CreatePlayerProto.CreatePlayer.class, MSGIDProto.MSGID.CreatePlayerProto_CreatePlayer.getNumber());
        messageToCmdMap.put(PlayerInfoProto.ReqPlayerLogin.class, MSGIDProto.MSGID.PlayerInfoProto_ReqPlayerLogin.getNumber());
        messageToCmdMap.put(PlayerInfoProto.TestMessage.class, MSGIDProto.MSGID.PlayerInfoProto_TestMessage.getNumber());
        messageToCmdMap.put(PlayerInfoProto.ReqPlayers.class, MSGIDProto.MSGID.PlayerInfoProto_ReqPlayers.getNumber());
        messageToCmdMap.put(PlayerInfoProto.PlayerInfos.class, MSGIDProto.MSGID.PlayerInfoProto_PlayerInfos.getNumber());
        messageToCmdMap.put(TipsProto.Tips.class, MSGIDProto.MSGID.TipsProto_Tips.getNumber());
        messageToCmdMap.put(LoginProto.LoginRes.class, MSGIDProto.MSGID.LoginProto_LoginRes.getNumber());
        messageToCmdMap.put(SceneProto.ResEnterScene.class, MSGIDProto.MSGID.SceneProto_ResEnterScene.getNumber());
        messageToCmdMap.put(PlayerInfoProto.ReqLevelUp.class, MSGIDProto.MSGID.PlayerInfoProto_ReqLevelUp.getNumber());
        messageToCmdMap.put(SkillProto.ReqReleaseSkill.class, MSGIDProto.MSGID.SkillProto_ReqReleaseSkill.getNumber());
        messageToCmdMap.put(SceneProto.SceneMessage.class, MSGIDProto.MSGID.SceneProto_SceneMessage.getNumber());
        messageToCmdMap.put(SceneProto.ReqEnterScene.class, MSGIDProto.MSGID.SceneProto_ReqEnterScene.getNumber());
        
    }

}