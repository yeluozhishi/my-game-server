package com.whk.db.entity;

import com.whk.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_repository", schema = "game-server")
public class PlayerRepositoryEntity extends AbstractEntity<Long> {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data")
    private byte[] data;

}