package com.whk.mongodb.Entity;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * 删减属性，执行保存后，该字段也会被清除
 */
public class UserAccount {
    /** 用户名 */
    @Id
    private String userName;
    /** 创建时间 */
    private Long createTime;
    /** ip */
    private String ip;
    /** 密码 */
    private String password;

    private List<PlayerBase> playerBases;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PlayerBase> getPlayerBases() {
        return playerBases;
    }

    public void setPlayerBases(List<PlayerBase> playerBases) {
        this.playerBases = playerBases;
    }
}
