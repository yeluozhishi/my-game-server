package com.whk.net.Entity;

import com.whk.server.Server;

import java.util.List;

public class ResponseEntity <T>{
    // 错误编号
    private int code;
    /** 消息体 */
    private T data;
    // 错误详细
    private String errMsg;

    public ResponseEntity(){

    }

    public ResponseEntity(T data){
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
