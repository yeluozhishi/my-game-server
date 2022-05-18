package com.whk.mongodb.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Server")
public class Server implements Serializable {
    @Id
    private Integer id;
    private String serverName;
    private String ip;
    private Integer port;
    // 大区
    private Integer zone;

    public Server() {
    }

    public Server(Integer id, String serverName, String ip, Integer port, Integer zone) {
        this.id = id;
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
        this.zone = zone;
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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
