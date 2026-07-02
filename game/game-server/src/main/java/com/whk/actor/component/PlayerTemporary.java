package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.actor.attribute.Attributes;
import com.whk.db.entity.PlayerTemporaryEntity;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.service.player.PlayerTemporaryService;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerTemporary extends AbstractCacheableData<PlayerTemporaryEntity, Long> {
    // 属性
    @Tag(1)
    private Attributes attributes = new Attributes();

    @Tag(2)
    private long hp;

    @Tag(3)
    private long mp;

    @Tag(4)
    private int mapId;

    @Tag(5)
    private int line;


    @Override
    public Long getId() {
        return getEntity().getId();
    }

    @Override
    public void update() {
        getEntity().setData(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
        SpringUtils.getBean(PlayerTemporaryService.class).update(getId(), this);
    }
}
