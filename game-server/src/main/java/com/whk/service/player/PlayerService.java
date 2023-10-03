package com.whk.service.player;

import org.whk.game.entity.PlayerEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/21
 */
@Component
public class PlayerService extends AbstractBaseService<PlayerEntity, Long> {


    public Optional<PlayerEntity> findPlayerById(long id){
        return getBaseRepository().findById(id);
    }

}
