package com.whk.loadconfig.Ipml;

import com.whk.loadconfig.entity.ServerMessageDef;
import com.whk.loadconfig.FileXMLConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.text.MessageFormat;
import java.util.LinkedList;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "server_message")
public class ServerMessageXMLConfig extends FileXMLConfig<ServerMessageDef> {

    public String[] message = new String[0];

    public String getMessage(int code) {
        assert code >= 0 && code < message.length;
        return message[code];
    }

    public String getMessage(int code, String... args) {
        var msg = getMessage(code);
        return MessageFormat.format(msg, (Object) args);
    }

    @Override
    protected void afterLoad(LinkedList<ServerMessageDef> linkedList) {
        var temp = new String[linkedList.size()];
        linkedList.forEach(m -> temp[m.key] = m.value);
        if (temp.length > 0) {
            message = temp;
        }
    }

}
