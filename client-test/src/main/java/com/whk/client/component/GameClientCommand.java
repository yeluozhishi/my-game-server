package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.client.service.GameClientBoot;
import com.whk.net.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.logging.Logger;

@ShellComponent
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    private User user;

    private Logger logger = Logger.getLogger(GameClientCommand.class.getName());

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

    @ShellMethod("连接服务器：connect-server [host] [port]")
    public void connectServer(@ShellOption(defaultValue = "")String host, @ShellOption(defaultValue = "0")int port){

        if (!host.isEmpty()){
            if (port == 0){
                return;
            }
            config.setDefaultGameGatewayHost(host);
            config.setDefaultGameGatewayPort(port);
        }
        boot.launch();
    }

    @ShellMethod("发送消息：send-message")
    public void sendMessage(){
        Message message = new Message();
        message.setBody("hello".getBytes());
        message.setCommand(1);
        message.setUserNames(List.of(user.getUser()));
        var beansWithAnnotation = applicationContext.getBeansWithAnnotation(GameMessageHandler.class);
        System.out.println("1");
        boot.getChannel().writeAndFlush(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
