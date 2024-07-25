package com.whk.actor.component;

import com.whk.gamedb.entity.PlayerResourceEntity;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Resource extends AbstractComponent<PlayerResourceEntity> {
    @Tag(1)
    private HashMap<Integer, Long> coins = new HashMap<>();

    @Override
    public void save(byte[] data) {
        getEntity().setData(data);
    }
}
