package com.whk.service.player;


import com.whk.AbstractCacheableService;
import com.whk.actor.component.PlayerModule;
import com.whk.db.entity.PlayerModuleEntity;
import com.whk.net.kafka.MessageInnerCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerModuleService extends AbstractCacheableService<PlayerModuleEntity, Long, PlayerModule> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerModuleEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public PlayerModule transferToObject(PlayerModuleEntity playerModuleEntity) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(playerModuleEntity.getData(), PlayerModule.class);
    }

}
