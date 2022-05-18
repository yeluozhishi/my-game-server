package com.whk.net;

import java.io.Serializable;
import java.util.List;

public abstract class Message implements Serializable {
    private int command = 0;

    private List<String> userNames = null;

    private Boolean isComeFromClient;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Boolean getComeFromClient() {
        return isComeFromClient;
    }

    public void setComeFromClient(Boolean comeFromClient) {
        isComeFromClient = comeFromClient;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }
}
