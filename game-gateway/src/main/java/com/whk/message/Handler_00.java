package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.channel.GameChannel;
import com.whk.net.enity.Message;
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
        var user = UserMgr.INSTANCE.getUserByUsername(userName);



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
