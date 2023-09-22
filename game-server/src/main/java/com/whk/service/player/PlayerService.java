package com.whk.service.player;

import com.whk.db.entity.PlayerEntity;
import com.whk.db.repository.PlayerMapper;
import com.whk.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/21
 */
@Component
public class PlayerService implements BaseService {

    private PlayerMapper playerMapper;

    @Autowired
    public void setPlayerMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public Optional<PlayerEntity> findPlayerById(long id){
        return playerMapper.findById(id);
    }

}
