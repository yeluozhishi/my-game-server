package com.whk.rpc.model;

import java.io.Serializable;

/**
 * @author tangjie<https://github.com/tang-jie>
 * @filename:MessageResponse.java
 * @description:MessageResponse功能模块
 * @blogs http://www.cnblogs.com/jietang/
 * @since 2016/10/7
 */
public class MessageResponse implements Serializable {

    private String messageId;
    private String error;
    private Object [] result;
    private boolean returnNotNull;

    public boolean isReturnNotNull() {
        return returnNotNull;
    }

    public void setReturnNotNull(boolean returnNotNull) {
        this.returnNotNull = returnNotNull;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object[] getResult() {
        return result;
    }

    public void setResult(Object[] result) {
        this.result = result;
    }
}

