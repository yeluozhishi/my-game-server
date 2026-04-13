package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.script.ILevelScript;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;


@GameMessageHandler
@Slf4j
public class Handler01 {

    @HandlerDescription(desc = "升级")
    public void message100(PlayerInfoProto.ReqLevelUp message, long playerId) {
        ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(playerId);
    }


}
