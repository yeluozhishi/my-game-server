package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.Message;
import com.whk.service.SendToServerUtil;
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
        var user = UserMgr.INSTANCE.getUser(userName);

        SendToServerUtil.INSTANCE.sendMessage(message, user.get().getServerId());


    }

    /**
     * 角色登录
     * @param message
     */
    public void message_02(Message message){
//        message.getUserIds()


    }


}
