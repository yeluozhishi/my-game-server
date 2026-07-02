package com.whk.service.player;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.PlayerTemporary;
import com.whk.db.entity.PlayerTemporaryEntity;
import com.whk.net.kafka.MessageInnerCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerTemporaryService extends AbstractCacheableService<PlayerTemporaryEntity, Long, PlayerTemporary> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerTemporaryEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public PlayerTemporary createNullCacheableData() {
        PlayerTemporary playerTemporary = new PlayerTemporary();
        playerTemporary.setNull(true);
        return playerTemporary;
    }

    @Override
    public PlayerTemporary transferToObject(PlayerTemporaryEntity playerTemporaryEntity) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(playerTemporaryEntity.getData(), PlayerTemporary.class);
    }

}
