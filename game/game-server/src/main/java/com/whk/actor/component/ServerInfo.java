package com.whk.actor.component;

import lombok.Data;

@Data
public class ServerInfo {

    /**
     * 网关
     */
    private String gateTopic;

    /**
     * 网关id，数据保存
     */
    private int gateServerId;

    /**
     * 服务器id，数据保存
     */
    private int serverId;

    /**
     * 当前服务器id，数据变更推送
     */
    private long presentServerId;


    public boolean inSceneServer() {
        return serverId != presentServerId;
    }
}
