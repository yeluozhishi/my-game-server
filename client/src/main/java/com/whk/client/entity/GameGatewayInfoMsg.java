package com.whk.client.entity;

public class GameGatewayInfoMsg{
    private String ip;
    private int port;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "GameGatewayInfoMsg{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", token='" + token + '\'' +
                '}';
    }
}
