package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;
import com.whk.rpc.api.IRpcHelloService;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.user.UserMgr;

@GameMessageHandler
public class Handler_00 {

    public void message_00(Message message){

    }

    /**
     * 创建角色
     * @param message
     */
    public void message_01(Message message){
        var userName = message.getBody().getString("userName");
        // 检查所选服的角色
        var now = System.currentTimeMillis();
        var user = UserMgr.INSTANCE.getUserByUsername(userName);
        var hello = RpcProxyHolder.INSTANCE.<IRpcHelloService>getInstance(IRpcHelloService.class, 2);
        var msg = hello.hello("1111");
        System.out.println("耗时：" + (System.currentTimeMillis() - now));

    }

    /**
     * 角色登录
     * @param message
     */
    public void message_02(Message message){
        var playerId = message.getPlayerId();
        var userId = message.getBody().getString("userId");
        UserMgr.INSTANCE.playerLogin(playerId, userId);

    }


}
