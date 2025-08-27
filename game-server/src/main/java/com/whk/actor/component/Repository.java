package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.db.entity.PlayerRepositoryEntity;
import com.whk.module.Storage;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.service.player.PlayerRepositoryService;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库
 */
@Getter
@Setter
public class Repository extends AbstractCacheableData<PlayerRepositoryEntity, Long> {

    @Tag(1)
    private Storage storage = new Storage();


    @Override
    public void updata() {
        getEntity().setData(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
        SpringUtils.getBean(PlayerRepositoryService.class).update(getId(), this);
    }

    @Override
    public Long getId() {
        return getEntity().getId();
    }
}
