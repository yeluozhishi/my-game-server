package com.whk.net.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRequest implements Serializable {
    private boolean noReturnAndNonBlocking;
    private String messageId;

    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;

    private String responseTopic;

}