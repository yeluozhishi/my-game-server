package com.whk.db.entity;

import com.whk.IEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player", schema = "game-server")
public class PlayerEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "career")
    private Integer career;

    @Column(name = "sex")
    private Byte sex;

    @Column(name = "last_login")
    private Long lastLogin;

    @Column(name = "name")
    private String name;
}