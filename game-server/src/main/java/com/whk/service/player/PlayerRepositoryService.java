package com.whk.service.player;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.Repository;
import com.whk.db.entity.PlayerRepositoryEntity;
import com.whk.net.kafka.MessageInnerCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerRepositoryService extends AbstractCacheableService<PlayerRepositoryEntity, Long, Repository> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerRepositoryEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public Repository transferToObject(PlayerRepositoryEntity playerRepositoryEntity) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(playerRepositoryEntity.getData(), Repository.class);
    }
}
