package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.db.entity.PlayerBagEntity;
import com.whk.module.Item;
import com.whk.module.Storage;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.service.player.PlayerBagService;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 背包
 */
@Getter
@Setter
public class Bag extends AbstractCacheableData<PlayerBagEntity, Long> {
    // 资源
    @Tag(1)
    private Map<Integer, Long> coins = new ConcurrentHashMap<>();

    // 存储
    @Tag(2)
    private Storage storage = new Storage();

    // 佩戴道具  index -> Item
    @Tag(3)
    private Map<Integer, Item> equip = new ConcurrentHashMap<>();

    @Override
    public void updata() {
        getEntity().setBagData(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
        SpringUtils.getBean(PlayerBagService.class).update(getId(), this);
    }

    @Override
    public Long getId() {
        return getEntity().getId();
    }
}
