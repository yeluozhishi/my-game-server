package com.whk.net;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private int command = 0;

    private List<String> userNames = null;

    private Boolean isComeFromClient;

    private int toServerId;

    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Boolean getComeFromClient() {
        return isComeFromClient;
    }

    public int getToServerId() {
        return toServerId;
    }

    public void setToServerId(int toServerId) {
        this.toServerId = toServerId;
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

    @Override
    public String toString() {
        return "Message{" +
                "command=" + command +
                ", userNames=" + userNames +
                ", isComeFromClient=" + isComeFromClient +
                ", toServerId=" + toServerId +
                '}';
    }
}
