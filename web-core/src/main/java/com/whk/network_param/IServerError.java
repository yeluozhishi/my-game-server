package com.whk.network_param;

public interface IServerError {
    // 错误码
    public int getErrorCode();

    // 错误信息
    public String getErrorDesc();

    public String toString();
}
