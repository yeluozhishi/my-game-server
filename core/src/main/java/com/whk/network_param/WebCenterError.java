package com.whk.network_param;

import java.util.concurrent.atomic.AtomicInteger;

public enum WebCenterError implements IServerError{



    UNKNOWN(-1, "游戏中心未知异常"),
    LOGIN_ERROR(2, "用户名或密码错误"),
    EXIST_ACCOUNT(3, "用户已存在"),
    NOT_GAME_GATE(4, "没有游戏网关信息"),
    TOKEN_FAILED(8, "token错误"),
    TOKEN_VOID(9, "token为空");




    private int code;
    private String errorDesc;

    WebCenterError(int code, String errorDesc) {
        this.code = code;
        this.errorDesc = errorDesc;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }

    @Override
    public String toString(){
        return "errorCode:" + this.code + ";errorDesc:" + this.errorDesc;
    }
}
