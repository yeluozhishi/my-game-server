package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.db.entity.UserPlayerEntity;
import com.whk.db.entity.UserPlayerPK;
import com.whk.service.user.UserPlayerService;
import lombok.Getter;
import lombok.Setter;

/**
 * 背包
 */
@Getter
@Setter
public class UserPlayer extends AbstractCacheableData<UserPlayerEntity, UserPlayerPK> {

    private long userId;

    private long playerId;

    @Override
    public void update() {
        getEntity().setUserId(userId);
        getEntity().setPlayerId(playerId);
        SpringUtils.getBean(UserPlayerService.class).update(getEntity().getUserId(), this);
    }

    @Override
    public UserPlayerPK getId() {
        return getEntity().getId();
    }
}
