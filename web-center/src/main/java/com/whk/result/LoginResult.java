package com.whk.result;

public class LoginResult {
    private String id;
    private String token;
    private String gate_ip;
    private int gate_port;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGate_ip() {
        return gate_ip;
    }

    public void setGate_ip(String gate_ip) {
        this.gate_ip = gate_ip;
    }

    public int getGate_port() {
        return gate_port;
    }

    public void setGate_port(int gate_port) {
        this.gate_port = gate_port;
    }
}
