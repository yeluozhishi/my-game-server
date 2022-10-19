package com.whk.mongodb.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 删减属性，执行保存后，该字段也会被清除
 */
@Document(collection = "AdminAccount")
public class AdminAccount {

    /** 用户名 */
    @Id
    private String userName;

    /** 创建时间 */
    private Long createTime;

    /** 密码 */
    private String password;

    public AdminAccount() {
    }

    public AdminAccount(String userName, Long createTime, String password) {
        this.userName = userName;
        this.createTime = createTime;
        this.password = password;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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
}
