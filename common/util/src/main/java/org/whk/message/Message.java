package org.whk.message;

import java.io.Serializable;

public class Message implements Serializable {
    private int command;

    private Long playerId;

    private MapBean body;

    public Message() {
    }

    public Message(int command, Long playerId, MapBean body) {
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

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Message clone(){
        return new Message(command, playerId, (MapBean) body.clone());
    }
}
