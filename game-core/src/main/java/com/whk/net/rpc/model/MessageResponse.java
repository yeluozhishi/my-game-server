package com.whk.net.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageResponse implements Serializable {

    private String messageId;
    private String error;
    private Object [] result;
    private boolean returnNotNull;

    private String topic;
}

