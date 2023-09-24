package org.whk.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    public String topic;

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

    public void setInstanceId(String instanceId){
        topic = instanceId;
    }

    public Message clone(){
        return new Message(command, playerId, (MapBean) body.clone());
    }
}
