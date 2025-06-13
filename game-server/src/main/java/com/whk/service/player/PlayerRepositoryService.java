package com.whk.service.player;

import com.whk.actor.component.Repository;
import com.whk.gamedb.entity.PlayerRepositoryEntity;
import com.whk.service.AbstractBaseService;
import com.whk.service.AbstractComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerRepositoryService extends AbstractComponentService<PlayerRepositoryEntity, Repository> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerRepositoryEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }

    @Override
    public byte[] transferToObject(PlayerRepositoryEntity playerRepositoryEntity) {
        return playerRepositoryEntity.getData();
    }

    @Override
    public Class<Repository> getComponentClass() {
        return Repository.class;
    }
}
