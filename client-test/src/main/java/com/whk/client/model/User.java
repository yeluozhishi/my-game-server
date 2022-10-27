package com.whk.client.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName = "whk";
    private String token;
    private String pwd = "123";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
