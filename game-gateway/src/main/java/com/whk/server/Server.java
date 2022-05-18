package com.whk.server;

import java.io.Serializable;

public class Server implements Serializable {
    private int id;
    private String serverName;
    private String ip;
    private int port;
    // 大区
    private int zone;

    public Server() {
    }

    public Server(int id, String serverName, String ip, int port, int zone) {
        this.id = id;
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
        this.zone = zone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }
}
