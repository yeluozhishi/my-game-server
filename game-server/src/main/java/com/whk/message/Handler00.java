package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){

    }

    /**
     * 登录角色
     */
    public void message02(Message message){
        var userName = message.getBody().getString("userName");
        var gateInstanceId = message.getBody().getString("instanceId");
        var pid = message.getBody().getString("pid");
        PlayerMgr.INSTANCE.creatPlayer(userName, gateInstanceId, pid);

    }


}
