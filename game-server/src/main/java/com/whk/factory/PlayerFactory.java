package com.whk.factory;

import com.whk.actor.Player;
import com.whk.net.channel.GameChannel;

public class PlayerFactory {

    public static Player createPlayer(String playerId, String gateInstanceId){
        GameChannel gameChannel = new GameChannel();
        Player player = new Player(playerId, gameChannel, gateInstanceId, true);
        player.kind = 1;
        player.sex = 1;
        player.init();
        return player;
    }


    public static Player buildPlayer(String playerId, GameChannel gameChannel, String gateInstanceId){
        Player player = new Player(playerId, gameChannel, gateInstanceId, true);
        player.init();
        return player;
    }



}
