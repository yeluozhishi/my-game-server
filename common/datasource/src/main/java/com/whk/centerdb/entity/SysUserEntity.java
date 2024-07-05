package com.whk.centerdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sys_user", schema = "admin")
public class SysUserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @NotNull
    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Size(max = 30)
    @NotNull
    @Column(name = "nick_name", nullable = false, length = 30)
    private String nickName;

    @Size(max = 2)
    @Column(name = "user_type", length = 2)
    private String userType;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 11)
    @Column(name = "phonenumber", length = 11)
    private String phonenumber;

    @Column(name = "sex")
    private Character sex;

    @Size(max = 100)
    @Column(name = "avatar", length = 100)
    private String avatar;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @Size(max = 128)
    @Column(name = "login_ip", length = 128)
    private String loginIp;

    @Column(name = "login_date")
    private Instant loginDate;

    @Column(name = "status")
    private Character status;

    @Size(max = 64)
    @Column(name = "create_by", length = 64)
    private String createBy;

    @Column(name = "create_time")
    private Instant createTime;

    @Size(max = 64)
    @Column(name = "update_by", length = 64)
    private String updateBy;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "del_flag")
    private Character delFlag;

    @Size(max = 500)
    @Column(name = "remark", length = 500)
    private String remark;

}