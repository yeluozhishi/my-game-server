package com.whk.scene.actor;

import com.whk.actor.PlayerActor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerActorMgr {
    INSTANCE;

    private final Map<Long, PlayerActor> playerActors = new ConcurrentHashMap<>();

    public Optional<PlayerActor> getPlayer(long playerId) {
        return Optional.ofNullable(playerActors.get(playerId));
    }


    public void addPlayerActor(PlayerActor actor) {
        playerActors.put(actor.getId(), actor);
    }
}
