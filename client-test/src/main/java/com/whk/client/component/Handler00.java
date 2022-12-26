package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.client.model.UserMgr;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
        var user = UserMgr.getUser();
        var l = message.getBody().<MapBean>getList("l");
        var m = l.stream().findAny();
        m.ifPresent(mapBean -> user.setPlayerId(mapBean.getString("id")));

    }

    public void message01(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
        var user = UserMgr.getUser();
        var pid = message.getBody().getString("pid");
        if (pid != null) user.setPlayerId(pid);
    }

    public void message02(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
    }

    public void message03(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
    }

    public void message04(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
    }

}
