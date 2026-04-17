package com.whk.db.entity;

import java.io.Serializable;

public record UserPlayerPK(long userId, long playerId) implements Serializable {}
