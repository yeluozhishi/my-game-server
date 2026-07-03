package com.whk.db.entity;

import com.whk.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player", schema = "game-server")
public class PlayerEntity extends AbstractEntity<Long> {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "career")
    private Integer career;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "last_login")
    private Long lastLogin;

    @Column(name = "name")
    private String name;
}