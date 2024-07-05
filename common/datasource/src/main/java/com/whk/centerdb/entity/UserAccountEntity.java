package com.whk.centerdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_account", schema = "admin")
public class UserAccountEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "user_name")
    private String userName;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Instant createTime;

    @Size(max = 255)
    @Column(name = "ip")
    private String ip;

    @Size(max = 255)
    @Column(name = "open_id")
    private String openId;

}