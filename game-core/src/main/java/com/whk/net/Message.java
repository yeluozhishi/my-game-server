package com.whk.net;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Message implements Serializable {
    private int command = 0;

    private List<String> userIds = null;

    private Boolean isComeFromClient;

    private int toServerId;

    private Map body;

    private int groupId;

    private int serverId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public Map getBody() {
        return body;
    }

    public void setBody(Map body) {
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

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "Message{" +
                "command=" + command +
                ", userNames=" + userIds +
                ", isComeFromClient=" + isComeFromClient +
                ", toServerId=" + toServerId +
                '}';
    }
}
