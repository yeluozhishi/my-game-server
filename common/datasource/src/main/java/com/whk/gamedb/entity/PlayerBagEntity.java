package com.whk.gamedb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_bag", schema = "game-server")
public class PlayerBagEntity implements IEntity{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bagData")
    private byte[] bagData;

}