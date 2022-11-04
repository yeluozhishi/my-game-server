package com.whk.server;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Server implements Serializable {
    /**
     * 服务器id
     */
    private Integer id;

    /**
     * 大区
     */
    private Integer zone;

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

    public Server(Integer id, Integer zone, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime) {
        this.id = id;
        this.zone = zone;
        this.serverName = serverName;
        this.openServerTime = openServerTime;
        this.openEntranceTime = openEntranceTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
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
}
