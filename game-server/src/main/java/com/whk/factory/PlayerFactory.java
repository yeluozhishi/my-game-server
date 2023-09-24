package com.whk.factory;


import com.whk.actor.Player;
import com.whk.db.entity.PlayerEntity;
import com.whk.net.channel.GameChannel;

public class PlayerFactory {

    public static Player createPlayer(PlayerEntity playerEntity, String gateInstanceId){
        GameChannel gameChannel = new GameChannel();
        Player player = new Player(playerEntity.getId(), gameChannel, gateInstanceId, true);
        player.career = playerEntity.getCareer();
        player.sex = playerEntity.getSex();
        return player;
    }


    public static Player buildPlayer(Long playerId, GameChannel gameChannel, String gateInstanceId){
        Player player = new Player(playerId, gameChannel, gateInstanceId, true);
        player.init();
        return player;
    }



}
