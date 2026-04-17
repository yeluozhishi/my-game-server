package com.whk.service.player;

import com.whk.AbstractCacheableService;
import com.whk.actor.component.BasicInfo;
import com.whk.db.entity.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author wanghongkun
 */
@Component
public class PlayerService extends AbstractCacheableService<PlayerEntity, Long, BasicInfo> {

    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerEntity, Long> baseRepository) {
        super.baseRepository = baseRepository;
    }

    @Override
    public BasicInfo createNullCacheableData() {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setNull(true);
        return basicInfo;
    }

    @Override
    public BasicInfo transferToObject(PlayerEntity playerEntity) {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setName(playerEntity.getName());
        basicInfo.setSex(playerEntity.getSex());
        basicInfo.setCareer(playerEntity.getCareer());
        basicInfo.setLastLogin(playerEntity.getLastLogin());
        return basicInfo;
    }
}
