package com.whk.client.component;

import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
import com.whk.net.MapBean;
import com.whk.net.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@ShellComponent
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    private User user;

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

    public void setUser(User user) {
        this.user = user;
    }

    @ShellMethod("连接服务器：connect-server")
    public void connectServer(){
        GameClientInitService initService = new GameClientInitService(config, this);
        initService.login();
        initService.showServerList();

    }

    @ShellMethod("选区：chose-server")
    public void choseServer(@ShellOption(defaultValue = "0")int serverId, @ShellOption(defaultValue = "0")String playerId){
        boot.launch();

        // 用户信息注册到网关，获取区服
        Message message = new Message(0x0, null, true, 1,
                MapBean.MapBean(Map.of("token", user.getToken())), 1, serverId);
        boot.getChannel().writeAndFlush(message);


        System.out.println("区服：");
    }

    @ShellMethod("发送消息：send-message [msg]")
    public void sendMessage(@ShellOption(defaultValue = "") String msg){
        Message message = new Message();
        message.setBody(MapBean.MapBean(Map.of("msg", msg)));
        message.setCommand(0);
        message.setComeFromClient(true);
        message.setUserIds(List.of(user.getUserName()));
        message.setToServerId(1);
        boot.getChannel().writeAndFlush(message);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void sendMessage1(@ShellOption(defaultValue = "") String msg){
        Message message = new Message();
        message.setBody(MapBean.MapBean(Map.of("msg", msg)));
        message.setCommand(1);
        message.setComeFromClient(true);
        message.setUserIds(List.of(user.getUserName()));
        message.setToServerId(1);
        boot.getChannel().writeAndFlush(message);
    }
}
