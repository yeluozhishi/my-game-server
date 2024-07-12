package com.whk.actor.component;

import lombok.Data;

@Data
public class ServerInfo {

    /**
     * 网关
     */
    private String gateTopic;

    /**
     * 服务器id，数据保存
     */
    private Integer serverId;

    /**
     * 当前服务器id，数据变更推送
     */
    private Integer presentServerId;
}
