package com.whk.script;

import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.protobuf.message.SceneProto;
import script.scriptInterface.IScript;

import java.lang.reflect.InvocationTargetException;

public interface IUserScript extends IScript {
    void noticeEnterSceneState(int serverId, long playerId);

    void createPlayer(CreatePlayerProto.CreatePlayer message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void playerLogin(PlayerInfoProto.ReqPlayerLogin message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void getPlayerList(PlayerInfoProto.ReqPlayers message, long userId);

    void enterScene(SceneProto.ReqEnterScene message, long userId);

    void testMsg(PlayerInfoProto.TestMessage message, long userId);
}
