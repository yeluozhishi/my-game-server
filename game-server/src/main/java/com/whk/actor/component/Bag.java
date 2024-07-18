package com.whk.actor.component;

import com.whk.gamedb.entity.PlayerBagEntity;
import com.whk.module.Item;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Bag extends AbstractComponent<PlayerBagEntity> {
    @Tag(1)
    private HashMap<Long, Item> items = new HashMap<>();
}
