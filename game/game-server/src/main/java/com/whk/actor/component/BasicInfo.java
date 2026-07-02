package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.db.entity.PlayerEntity;
import com.whk.service.player.PlayerService;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicInfo extends AbstractCacheableData<PlayerEntity, Long> {

    /**
     * 名字
     */
    @Tag(1)
    private String name;

    /**
     * 性别
     */
    @Tag(2)
    private int sex;

    /**
     * 职业
     */
    @Tag(3)
    private int career;

    /**
     * 最后登录时间
     */
    @Tag(4)
    private long lastLogin;

    /**
     * 创建时间
     */
    @Tag(5)
    private long createTime;

    @Override
    public Long getId() {
        return getEntity().getId();
    }

    @Override
    public void update() {
        getEntity().setName(name);
        getEntity().setSex(sex);
        getEntity().setCareer(career);
        getEntity().setLastLogin(lastLogin);
        getEntity().setCreateTime(createTime);
        SpringUtils.getBean(PlayerService.class).update(getId(), this);
    }
}
