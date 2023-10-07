package com.whk.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
@Entity
@Table(name = "sys_user", schema = "admin", catalog = "")
public class SysUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic
    @Column(name = "dept_id", nullable = true)
    private Long deptId;
    @Basic
    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;
    @Basic
    @Column(name = "nick_name", nullable = false, length = 30)
    private String nickName;
    @Basic
    @Column(name = "user_type", nullable = true, length = 2)
    private String userType;
    @Basic
    @Column(name = "email", nullable = true, length = 50)
    private String email;
    @Basic
    @Column(name = "phonenumber", nullable = true, length = 11)
    private String phonenumber;
    @Basic
    @Column(name = "sex", nullable = true, length = 1)
    private String sex;
    @Basic
    @Column(name = "avatar", nullable = true, length = 100)
    private String avatar;
    @Basic
    @Column(name = "password", nullable = true, length = 100)
    private String password;
    @Basic
    @Column(name = "login_ip", nullable = true, length = 128)
    private String loginIp;
    @Basic
    @Column(name = "login_date", nullable = true)
    private Timestamp loginDate;
    @Basic
    @Column(name = "status", nullable = true, length = 1)
    private String status;
    @Basic
    @Column(name = "create_by", nullable = true, length = 64)
    private String createBy;
    @Basic
    @Column(name = "create_time", nullable = true)
    private Timestamp createTime;
    @Basic
    @Column(name = "update_by", nullable = true, length = 64)
    private String updateBy;
    @Basic
    @Column(name = "update_time", nullable = true)
    private Timestamp updateTime;
    @Basic
    @Column(name = "del_flag", nullable = true, length = 1)
    private String delFlag;
    @Basic
    @Column(name = "remark", nullable = true, length = 500)
    private String remark;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserEntity that = (SysUserEntity) o;
        return userId == that.userId && Objects.equals(deptId, that.deptId) && Objects.equals(userName, that.userName) && Objects.equals(nickName, that.nickName) && Objects.equals(userType, that.userType) && Objects.equals(email, that.email) && Objects.equals(phonenumber, that.phonenumber) && Objects.equals(sex, that.sex) && Objects.equals(avatar, that.avatar) && Objects.equals(password, that.password) && Objects.equals(loginIp, that.loginIp) && Objects.equals(loginDate, that.loginDate) && Objects.equals(status, that.status) && Objects.equals(createBy, that.createBy) && Objects.equals(createTime, that.createTime) && Objects.equals(updateBy, that.updateBy) && Objects.equals(updateTime, that.updateTime) && Objects.equals(delFlag, that.delFlag) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, deptId, userName, nickName, userType, email, phonenumber, sex, avatar, password, loginIp, loginDate, status, createBy, createTime, updateBy, updateTime, delFlag, remark);
    }
}
