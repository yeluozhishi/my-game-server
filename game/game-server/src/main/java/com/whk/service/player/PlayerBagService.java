package com.whk.service.player;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.Bag;
import com.whk.db.entity.PlayerBagEntity;
import com.whk.net.kafka.MessageInnerCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerBagService extends AbstractCacheableService<PlayerBagEntity, Long, Bag> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerBagEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public Bag createNullCacheableData() {
        Bag bag = new Bag();
        bag.setNull(true);
        return bag;
    }

    @Override
    public Bag transferToObject(PlayerBagEntity playerBagEntity) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(playerBagEntity.getBagData(), Bag.class);
    }

}
