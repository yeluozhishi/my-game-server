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
            throw new IllegalArgumentException("未知协议号: " + message.getCommand());
        }
        return prototype.getParserForType().parseFrom(message.getPayload());
    }

    public int getCmd(Class<?> c) {
        return messageToCmdMap.getOrDefault(c, -1);
    }


    public Message getMessageClass(int cmd) {
        return cmdToMessageMap.get(cmd);
    }


    public void setCmdToMessageMap() {
        cmdToMessageMap.put(10, LoginProto.LoginReq.getDefaultInstance());
        cmdToMessageMap.put(11, CreatePlayerProto.CreatePlayer.getDefaultInstance());
        cmdToMessageMap.put(12, PlayerInfoProto.ReqPlayerLogin.getDefaultInstance());
        cmdToMessageMap.put(13, PlayerInfoProto.TestMessage.getDefaultInstance());
        cmdToMessageMap.put(14, PlayerInfoProto.ReqPlayers.getDefaultInstance());
        cmdToMessageMap.put(15, SceneProto.ReqEnterScene.getDefaultInstance());
        cmdToMessageMap.put(16, PlayerInfoProto.PlayerInfos.getDefaultInstance());
        cmdToMessageMap.put(17, TipsProto.Tips.getDefaultInstance());
        cmdToMessageMap.put(18, LoginProto.LoginRes.getDefaultInstance());
        cmdToMessageMap.put(19, SceneProto.ResEnterScene.getDefaultInstance());
        cmdToMessageMap.put(100, PlayerInfoProto.ReqLevelUp.getDefaultInstance());
        cmdToMessageMap.put(101, PlayerInfoProto.ResLevelUp.getDefaultInstance());
        cmdToMessageMap.put(102, SkillProto.ReqReleaseSkill.getDefaultInstance());
        cmdToMessageMap.put(103, SceneProto.SceneMessage.getDefaultInstance());
        
    }

    public void setMessageToCmdMap() {
        messageToCmdMap.put(LoginProto.LoginReq.class, 10);
        messageToCmdMap.put(CreatePlayerProto.CreatePlayer.class, 11);
        messageToCmdMap.put(PlayerInfoProto.ReqPlayerLogin.class, 12);
        messageToCmdMap.put(PlayerInfoProto.TestMessage.class, 13);
        messageToCmdMap.put(PlayerInfoProto.ReqPlayers.class, 14);
        messageToCmdMap.put(SceneProto.ReqEnterScene.class, 15);
        messageToCmdMap.put(PlayerInfoProto.PlayerInfos.class, 16);
        messageToCmdMap.put(TipsProto.Tips.class, 17);
        messageToCmdMap.put(LoginProto.LoginRes.class, 18);
        messageToCmdMap.put(SceneProto.ResEnterScene.class, 19);
        messageToCmdMap.put(PlayerInfoProto.ReqLevelUp.class, 100);
        messageToCmdMap.put(PlayerInfoProto.ResLevelUp.class, 101);
        messageToCmdMap.put(SkillProto.ReqReleaseSkill.class, 102);
        messageToCmdMap.put(SceneProto.SceneMessage.class, 103);
        
    }

}