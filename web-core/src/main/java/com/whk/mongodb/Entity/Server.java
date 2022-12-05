package com.whk.mongodb.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "Server")
public class Server implements Serializable {
    /**
     * 服务器id
     */
    @Id
    private Integer id;

    /**
     * 大区
     */
    private Integer zone;

    /**
     * 服务器实例id
     */
    private String instanceId;

    /**
     * 类型
     */
    private Integer serverType;

    /**
     * 服务器名
     */
    private String serverName;

    /**
     * 开服时间
     */
    private LocalDateTime openServerTime;

    /**
     * 开放入口时间
     */
    private LocalDateTime openEntranceTime;

    public Server() {
    }

    public Server(Integer id, Integer zone, String instanceId, Integer serverType, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime) {
        this.id = id;
        this.zone = zone;
        this.instanceId = instanceId;
        this.serverType = serverType;
        this.serverName = serverName;
        this.openServerTime = openServerTime;
        this.openEntranceTime = openEntranceTime;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public LocalDateTime getOpenServerTime() {
        return openServerTime;
    }

    public void setOpenServerTime(LocalDateTime openServerTime) {
        this.openServerTime = openServerTime;
    }

    public LocalDateTime getOpenEntranceTime() {
        return openEntranceTime;
    }

    public void setOpenEntranceTime(LocalDateTime openEntranceTime) {
        this.openEntranceTime = openEntranceTime;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }
}
