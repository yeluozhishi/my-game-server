package com.whk.client.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class UserMgr {

    private static UserMgr instance = new UserMgr();


    private Map<Long, User> users = new HashMap<>();

    private Map<Long, User> usersByPlayerId = new HashMap<>();

    private UserMgr(){}

    public static UserMgr getInstance(){
        return instance;
    }


    public void addUser(User user){
        users.put(user.getUserId(), user);
    }

    public User getUser(long userId){
        return users.get(userId);
    }

    public User getUserByPlayerId(long playerId){
       return usersByPlayerId.get(playerId);
    }

}
