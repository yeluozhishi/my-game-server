package com.whk.factory;


import com.whk.actor.Player;
import org.whk.game.entity.PlayerEntity;

public class PlayerFactory {

    public static Player createPlayer(PlayerEntity playerEntity, String gateInstanceId){
        Player player = new Player(playerEntity.getId(), gateInstanceId, true);
        player.setCareer(playerEntity.getCareer());
        player.setSex(playerEntity.getSex());
        player.setUserAccountId(playerEntity.getUserAccountId());
        return player;
    }


    public static Player buildPlayer(Long playerId, String gateInstanceId){
        Player player = new Player(playerId, gateInstanceId, true);
        player.init();
        return player;
    }



}
