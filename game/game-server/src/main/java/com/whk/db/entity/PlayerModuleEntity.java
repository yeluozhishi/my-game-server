package com.whk.db.entity;

import com.whk.IEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_module", schema = "game-server")
public class PlayerModuleEntity implements IEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "data")
    private byte[] data;

}