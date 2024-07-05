package com.whk.centerdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_info", schema = "admin")
public class PlayerInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "career")
    private Integer career;

    @Column(name = "sex")
    private Byte sex;

    @Column(name = "last_login")
    private Long lastLogin;

    @Column(name = "server_id")
    private Integer serverId;

}