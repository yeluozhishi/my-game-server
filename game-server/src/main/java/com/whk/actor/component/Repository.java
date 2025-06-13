package com.whk.actor.component;

import com.whk.SpringUtils;
import com.whk.gamedb.entity.PlayerRepositoryEntity;
import com.whk.module.Storage;
import com.whk.service.player.PlayerRepositoryService;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库
 */
@Getter
@Setter
public class Repository extends AbstractComponent<PlayerRepositoryEntity> {

    @Tag(1)
    private Storage storage = new Storage();


    @Override
    public void updata(byte[] data) {
        getEntity().setData(data);
        SpringUtils.getBean(PlayerRepositoryService.class).updateComponent(getId(), this);
    }

    @Override
    public long getId() {
        return getEntity().getId();
    }
}
