package com.whk.mongodb.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 删减属性，执行保存后，该字段也会被清除
 */
@Document(collection = "UserAccount")
public class UserAccount {
    // 用户id
    @Id
    private String user_name;

    private List<PlayerBase> players;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<PlayerBase> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerBase> players) {
        this.players = players;
    }

    public void addPlayer(PlayerBase player) {
        this.players.add(player);
    }
}
