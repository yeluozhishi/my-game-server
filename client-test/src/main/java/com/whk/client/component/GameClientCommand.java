package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
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

    @ShellMethod("连接服务器：connect-server")
    public void connectServer(){
        GameClientInitService initService = new GameClientInitService(config, this);
        initService.login();

        System.out.println("区服：");
    }

    @ShellMethod("选区：")
    public void choseServer(@ShellOption(defaultValue = "0")int serverId, @ShellOption(defaultValue = "0")String playerId){


        boot.launch();
    }

    @ShellMethod("发送消息：send-message [msg]")
    public void sendMessage(@ShellOption(defaultValue = "") String msg){
        Message message = new Message();
        message.setBody(Map.of("msg", msg));
        message.setCommand(0);
        message.setComeFromClient(true);
        message.setUserIds(List.of(user.getUserName()));
        message.setToServerId(1);
        var beansWithAnnotation = applicationContext.getBeansWithAnnotation(GameMessageHandler.class);
        boot.getChannel().writeAndFlush(message);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void sendMessage1(@ShellOption(defaultValue = "") String msg){
        Message message = new Message();
        message.setBody(Map.of("msg", msg));
        message.setCommand(1);
        message.setComeFromClient(true);
        message.setUserIds(List.of(user.getUserName()));
        message.setToServerId(1);
        var beansWithAnnotation = applicationContext.getBeansWithAnnotation(GameMessageHandler.class);
        boot.getChannel().writeAndFlush(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
