package com.whk.message;

public enum MESSAGE_CODE {
    操作成功(0),
    游戏中心未知异常(1),
    用户名或密码错误(2),
    用户已存在(3),
    没有游戏网关信息(4),
    token错误(5),
    token为空(6),
    重复插入(7),
    大区不存在(8),
    RPC请求超时(9),
    未配置跨服(10),
    未配置大跨服(11),
    未配置游戏服(12),
    不再使用的普通跨服(13),
    已达创角上限(14),
    还没有账户(15),
    已有角色(16),
    登录成功(17),
    创建角色成功(18),
    角色登录失败(19),
    已经接收消息(20),
    角色登录成功(21),
    升级成功(22),
    升级失败(23),
    进入场景失败(24),
    ;



    private final int code;

    MESSAGE_CODE(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
