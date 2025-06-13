package com.whk.service.player;

import com.whk.actor.component.Bag;
import com.whk.gamedb.entity.PlayerBagEntity;
import com.whk.service.AbstractComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerBagService extends AbstractComponentService<PlayerBagEntity, Bag> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerBagEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }

    @Override
    public byte[] transferToObject(PlayerBagEntity playerBagEntity) {
        return playerBagEntity.getBagData();
    }

    @Override
    public Class<Bag> getComponentClass() {
        return Bag.class;
    }
}
