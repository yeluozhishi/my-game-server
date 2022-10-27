package com.whk.client.entity;

public record GameGatewayInfoMsg(String ip, int port, String token, String instanceId, int zone){
    @Override
    public String toString() {
        return "GameGatewayInfoMsg{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", token='" + token + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", zone=" + zone +
                '}';
    }
}
