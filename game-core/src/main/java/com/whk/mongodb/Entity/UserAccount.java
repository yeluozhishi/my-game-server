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
    private String userName;

    private List<PlayerBase> players;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
