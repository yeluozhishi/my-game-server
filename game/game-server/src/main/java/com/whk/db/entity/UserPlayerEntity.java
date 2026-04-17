package com.whk.db.entity;

import com.whk.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Getter
@Setter
@Entity
@IdClass(UserPlayerPK.class)
@Table(name = "user_player", schema = "game-server")
public class UserPlayerEntity extends AbstractEntity<UserPlayerPK> {
    private transient UserPlayerPK id;
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Id
    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Override
    public @Nullable UserPlayerPK getId() {
        if (Objects.isNull(id)) id = new UserPlayerPK(userId, playerId);
        return id;
    }
}

