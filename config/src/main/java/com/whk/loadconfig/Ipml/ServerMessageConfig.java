package com.whk.loadconfig.Ipml;

import com.whk.entity.ServerMessageDef;
import com.whk.loadconfig.FileConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "server_message")
public class ServerMessageConfig extends FileConfig<ServerMessageDef> {

    public static Map<String, String> message = new HashMap<>();

    @Override
    protected void afterLoad(LinkedList<ServerMessageDef> linkedList) {
        linkedList.forEach(m -> message.put(m.key, m.value));
    }

}
