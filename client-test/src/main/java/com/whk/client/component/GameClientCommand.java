package com.whk.client.component;

import com.whk.client.config.GameClientConfig;
import com.whk.client.model.UserMgr;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


import java.util.logging.Logger;

@ShellComponent
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    private final Logger logger = Logger.getLogger(GameClientCommand.class.getName());

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setBoot(GameClientBoot boot) {
        this.boot = boot;
    }

    @Autowired
    public void setConfig(GameClientConfig config) {
        this.config = config;
    }

    @ShellMethod("连接服务器：connect-server")
    public void connectServer(){
        GameClientInitService initService = new GameClientInitService(config, this);
        initService.login();
        initService.showServerList();
        boot.launch();
    }

    @ShellMethod("选区：chose-server")
    public void choseServer(@ShellOption(defaultValue = "0")int serverId, @ShellOption(defaultValue = "0")String playerId){

        var user = UserMgr.getUser();
        // 选区，用户信息注册到网关，获取角色列表
//        MessageProto.Message message = new MessageProto.Message(0x0, null,
//                MapBean.MapBean(Map.of("token", user.getToken(), "userName", user.getUserName(), "serverId", 1, "playerId", "")));
//        user.setServerId(1);
//        boot.getChannel().writeAndFlush(message);


    }

    @ShellMethod("发送消息：send-message [msg]")
    public void sendMessage(@ShellOption(defaultValue = "") String msg){
//        Message message = new Message();
//        var user = UserMgr.getUser();
//        message.setBody(MapBean.MapBean(Map.of("msg", msg, "userName", user.getUserName(), "serverId", user.getServerId())));
//        message.setCommand(0x1);
//        message.setPlayerId(user.getPlayerId());
//        boot.getChannel().writeAndFlush(message);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void sendMessage1(@ShellOption(defaultValue = "") String msg){
//        Message message = new Message();
//        var user = UserMgr.getUser();
//        message.setBody(MapBean.MapBean(Map.of("msg", msg, "userName", user.getUserName())));
//        message.setCommand(0x2);
//        message.setPlayerId(user.getPlayerId());
//        boot.getChannel().writeAndFlush(message);
    }
}
