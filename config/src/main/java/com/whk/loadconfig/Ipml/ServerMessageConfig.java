package com.whk.loadconfig.Ipml;

import com.whk.entity.ServerMessageDef;
import com.whk.loadconfig.FileConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "server_message")
public class ServerMessageConfig extends FileConfig<ServerMessageDef> {

    public static String[] message = new String[0];

    public static String getMessage(int code){
        assert code >= 0 && code < message.length;
        return message[code];
    }

    public static String getMessage(int code, String... args){
        var msg = getMessage(code);
        return MessageFormat.format(msg, args);
    }

    @Override
    protected void afterLoad(LinkedList<ServerMessageDef> linkedList) {
        var temp = new String[linkedList.size()];
        linkedList.forEach(m -> temp[m.key] = m.value);
        if (temp.length > 0){
            message = temp;
        }
    }

}
