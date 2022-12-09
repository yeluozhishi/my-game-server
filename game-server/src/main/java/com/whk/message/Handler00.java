package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.mongodb.dao.UserAccountDao;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.messageholder.SendMessageHolder;
import com.whk.rpc.api.IRpcHelloService;
import com.whk.rpc.api.provider.RpcHelloServiceImpl;

import java.util.Map;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){
        System.out.println("get message_00" + message.getBody());

    }

    /**
     * 创建角色
     * @param message
     */
    public void message01(Message message){
        var userName = message.getBody().getString("userName");
        var gateInstanceId = message.getBody().getString("instanceId");
        PlayerMgr.INSTANCE.creatPlayer(userName, gateInstanceId);






    }


}
