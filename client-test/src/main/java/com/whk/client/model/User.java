package com.whk.client.model;

import java.io.Serializable;

public class User implements Serializable {
    private String user = "whk";
    private String token;
    private String pwd = "123";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
