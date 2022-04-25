package com.whk.client.component;

import com.whk.client.config.GameClientConfig;
import com.whk.client.entity.record.Message;
import com.whk.client.service.GameClientBoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Map;
import java.util.logging.Logger;

@ShellComponent
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    private Logger logger = Logger.getLogger(GameClientCommand.class.getName());

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

    @ShellMethod("发送消息： send-msg")
    public void sendMsg(){
        Message message = new Message(1, Map.of("op", "hello"));
        boot.getChannel().writeAndFlush(message);
    }

}
