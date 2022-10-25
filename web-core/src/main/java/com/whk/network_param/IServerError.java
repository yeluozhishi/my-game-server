package com.whk.network_param;

public interface IServerError {
    /**
     * 错误码
     * @return
     */
    public String getErrorCode();

    /**
     * 错误信息
     * @return
     */
    public String getErrorDesc();

}
