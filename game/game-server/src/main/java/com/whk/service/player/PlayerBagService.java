package com.whk.service.player;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.PlayerBag;
import com.whk.db.entity.PlayerBagEntity;
import com.whk.net.kafka.MessageInnerCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerBagService extends AbstractCacheableService<PlayerBagEntity, Long, PlayerBag> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerBagEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public PlayerBag createNullCacheableData() {
        PlayerBag playerBag = new PlayerBag();
        playerBag.setNull(true);
        return playerBag;
    }

    @Override
    public PlayerBag transferToObject(PlayerBagEntity playerBagEntity) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(playerBagEntity.getData(), PlayerBag.class);
    }

}
