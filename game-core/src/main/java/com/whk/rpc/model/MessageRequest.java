package com.whk.rpc.model;

import java.io.Serializable;

public class MessageRequest implements Serializable {
    private boolean noReturnAndNonBlocking;
    private String messageId;

    private String instanceId;
    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }

    public boolean isNoReturnAndNonBlocking() {
        return noReturnAndNonBlocking;
    }

    public void setNoReturnAndNonBlocking(boolean noReturnAndNonBlocking) {
        this.noReturnAndNonBlocking = noReturnAndNonBlocking;
    }
}

