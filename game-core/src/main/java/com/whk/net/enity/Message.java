package com.whk.net.enity;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private int command;

    private String playerId;

    private MapBean body;

    public Message() {
    }

    public Message(int command, String playerId, MapBean body) {
        this.command = command;
        this.playerId = playerId;
        this.body = body;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public MapBean getBody() {
        return body;
    }

    public void setBody(MapBean body) {
        this.body = body;
    }

    public void setErr(int errCode,String errMsg){
        body = new MapBean(errCode, errMsg);
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Message clone(){
        return new Message(command, playerId, (MapBean) body.clone());
    }
}
