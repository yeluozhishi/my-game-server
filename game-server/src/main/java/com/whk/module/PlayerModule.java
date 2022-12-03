package com.whk.module;

import com.whk.net.enity.MapBean;

public abstract class PlayerModule {
    /**
     * 初始化模块
     * @param isLogin 是否是登录
     */
    public abstract void init(boolean isLogin);

    /**
     * 生成需要发送到其他服的数据
     * 与{@link #initGainMessage(MapBean)}配套
     */
    public abstract MapBean generateMessageToOtherServer();

    /**
     * 利用其他服的数据初始化该模块
     * 与{@link #generateMessageToOtherServer()}配套
     */
    public abstract void initGainMessage(MapBean mapBean);

    public void syncToServer(){

    }
}
