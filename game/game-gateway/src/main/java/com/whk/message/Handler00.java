package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.protobuf.message.LoginProto;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.script.IUserScript;
import com.whk.threadpool.processor.ProcessorId;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.lang.reflect.InvocationTargetException;

@GameMessageHandler
@Slf4j
public class Handler00 {

    @HandlerDescription(desc = "用户登录", processorId = ProcessorId.LOGIN_PROCESSOR)
    public void message10(LoginProto.LoginReq message, long userId) {
        System.out.printf("userId  %d  已登录。%n", userId);
    }

    @HandlerDescription(desc = "角色创建", processorId = ProcessorId.PLAYER_PROCESSOR)
    public void message11(CreatePlayerProto.CreatePlayer message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).createPlayer(message, userId);
    }

    @HandlerDescription(desc = "角色登录")
    public void message12(PlayerInfoProto.ReqPlayerLogin message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).playerLogin(message, userId);
    }


    @HandlerDescription(desc = "测试消息")
    public void message13(PlayerInfoProto.TestMessage message, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).testMsg(message, userId);
    }


    @HandlerDescription(desc = "获取角色列表")
    public void message14(PlayerInfoProto.ReqPlayers message, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).getPlayerList(message, userId);
    }

    @HandlerDescription(desc = "进入场景")
    public void message15(SceneProto.ReqEnterScene message, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).enterScene(message, userId);
    }
}
