package com.whk.actor.component;

import com.whk.gamedb.entity.PlayerRepositoryEntity;
import com.whk.module.Storage;
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
    public void save(byte[] data) {
        getEntity().setData(data);
    }
}
