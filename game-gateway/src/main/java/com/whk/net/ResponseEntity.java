package com.whk.net;

import com.whk.server.Server;

import java.util.List;

public class ResponseEntity{
    // 错误编号
    private int code;
    // 消息体
    private List<Server> data;
    // 错误详细
    private String errMsg;

    public ResponseEntity(){

    }

    public ResponseEntity(List<Server> data){
        super();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Server> getData() {
        return data;
    }

    public void setData(List<Server> data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
