package com.whk.config;

import com.whk.entity.ServerMessageDef;
import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "server_message")
@Getter
public class ServerMessageConfig extends AbstractConfig<ServerMessageDef> {

    @Getter
    private static ServerMessageConfig instance = new ServerMessageConfig();


    private String[] message = new String[0];

    public String getMessage(int code) {
        if (code < 0 || code >= message.length) return "";
        return message[code];
    }

    public String getMessage(int code, String... args) {
        return MessageFormat.format(getMessage(code), (Object[]) args);
    }

    @Override
    public void afterLoad(List<ServerMessageDef> list) {
        var temp = new String[list.size()];
        list.forEach(m -> temp[m.code] = m.content);
        if (temp.length > 0) {
            message = temp;
        }
    }

    @Override
    public void setInstance() {
        instance = this;
    }
}
