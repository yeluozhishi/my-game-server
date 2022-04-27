package com.whk.net;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private int command = 0;

    private String user = null;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
