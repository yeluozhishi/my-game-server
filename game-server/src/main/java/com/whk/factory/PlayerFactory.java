package com.whk.factory;


import com.whk.actor.Player;
import com.whk.db.entity.PlayerEntity;

public class PlayerFactory {

    public static Player createPlayer(PlayerEntity playerEntity, String gateInstanceId){
        Player player = new Player(playerEntity.getId(), gateInstanceId, true);
        player.setCareer(playerEntity.getCareer());
        player.setSex(playerEntity.getSex());
        player.setUserAccountId(playerEntity.getUserAccountId());
        player.init();
        return player;
    }


    public static Player buildPlayer(PlayerEntity playerEntity, String gateInstanceId){
        Player player = new Player(playerEntity.getId(), gateInstanceId, true);
        player.setCareer(playerEntity.getCareer());
        player.setSex(playerEntity.getSex());
        player.setUserAccountId(playerEntity.getUserAccountId());
        player.init();
        return player;
    }



}
