package com.whk.client.component;

import com.whk.client.config.GameClientConfig;
import com.whk.client.model.UserMgr;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
import com.whk.protobuf.message.LoginProto;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.PlayerInfoProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

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
    public void choseServer(@ShellOption(defaultValue = "0")Integer serverId){

        var user = UserMgr.getUser();
        // 选区，用户信息注册到网关，获取角色列表
        LoginProto.LoginReq.Builder message = LoginProto.LoginReq.newBuilder();
        message.setServerId(serverId);
        message.setToken(user.getToken());
        message.setUserId(user.getUserId());
        MessageProto.Message.Builder message1 = MessageProto.Message.newBuilder();
        message1.setCommand(0);
        message1.setLoginReq(message);
        System.out.println("choseServer");
        user.setServerId(serverId);
        boot.getChannel().writeAndFlush(message1);
    }

    @ShellMethod("发送消息：send-message [msg]")
    public void chosePlayer(@ShellOption(defaultValue = "0")Long playerId){
        var user = UserMgr.getUser();
        user.setPlayerId(playerId);
        PlayerInfoProto.ReqPlayerLogin.Builder message = PlayerInfoProto.ReqPlayerLogin.newBuilder();
        message.setPlayerId(playerId);
        MessageProto.Message.Builder message1 = MessageProto.Message.newBuilder();
        message1.setCommand(2);
        message1.setReqPlayerLogin(message);
        boot.getChannel().writeAndFlush(message1);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void testMessage(){
        MessageProto.Message.Builder message1 = MessageProto.Message.newBuilder();
        message1.setCommand(3);
        boot.getChannel().writeAndFlush(message1);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void getPlayers(){
        MessageProto.Message.Builder message1 = MessageProto.Message.newBuilder();
        message1.setCommand(4);
        boot.getChannel().writeAndFlush(message1);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void levelUp(){
        MessageProto.Message.Builder message = MessageProto.Message.newBuilder();
        message.setCommand(100);
        boot.getChannel().writeAndFlush(message);
    }
}
