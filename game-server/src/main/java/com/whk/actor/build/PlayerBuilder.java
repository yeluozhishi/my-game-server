package com.whk.actor.build;


import com.whk.actor.Player;
import com.whk.gamedb.entity.PlayerEntity;

public class PlayerBuilder {

    public static Player createPlayer(PlayerEntity playerEntity, String gateInstanceId){
        Player player = new Player();
        player.setId(playerEntity.getId());
        var basicInfo = player.getBasicInfo();
        basicInfo.setGateInstanceId(gateInstanceId);
        basicInfo.setCareer(playerEntity.getCareer());
        basicInfo.setSex(playerEntity.getSex());
        player.init();
        return player;
    }

}
