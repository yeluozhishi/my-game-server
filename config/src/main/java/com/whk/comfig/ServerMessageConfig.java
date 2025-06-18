package com.whk.comfig;

import com.whk.entity.ServerMessageDef;
import com.whk.loadconfig.FileCSVConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.text.MessageFormat;
import java.util.LinkedList;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "server_message")
public class ServerMessageConfig extends FileCSVConfig<ServerMessageDef> {

    private String[] message = new String[0];

    public String getMessage(int code) {
        if (code < 0 || code >= message.length) return "";
        return message[code];
    }

    public String getMessage(int code, String... args) {
        return MessageFormat.format(getMessage(code), (Object[]) args);
    }

    @Override
    protected void afterLoad(LinkedList<ServerMessageDef> linkedList) {
        var temp = new String[linkedList.size()];
        linkedList.forEach(m -> temp[m.code] = m.content);
        if (temp.length > 0) {
            message = temp;
        }
    }

}
