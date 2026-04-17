package com.whk.service.user;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.UserPlayer;
import com.whk.db.entity.UserPlayerEntity;
import com.whk.db.entity.UserPlayerPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserPlayerService extends AbstractCacheableService<UserPlayerEntity, UserPlayerPK, UserPlayer> {
    @Override
    public UserPlayer createNullCacheableData() {
         UserPlayer userPlayer = new UserPlayer();
         userPlayer.setNull(true);
        return userPlayer;
    }

    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<UserPlayerEntity, UserPlayerPK> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public UserPlayer transferToObject(UserPlayerEntity userPlayerEntity) {
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setUserId(userPlayerEntity.getUserId());
        userPlayer.setPlayerId(userPlayerEntity.getPlayerId());
        return userPlayer;
    }
}
