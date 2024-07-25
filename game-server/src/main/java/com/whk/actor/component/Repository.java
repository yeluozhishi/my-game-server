package com.whk.actor.component;

import com.whk.gamedb.entity.PlayerRepositoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repository extends AbstractComponent<PlayerRepositoryEntity> {
    @Override
    public void save(byte[] data) {
        getEntity().setData(data);
    }
}
