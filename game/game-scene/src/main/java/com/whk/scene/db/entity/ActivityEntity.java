package com.whk.scene.db.entity;

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
@Table(name = "activity", schema = "game-scene")
public class ActivityEntity implements IEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "activity_data")
    private byte[] activityData;

}