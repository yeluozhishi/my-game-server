package com.whk.client.model;

public class UserMgr {

    public static User user;

    public static void init(User user){
        UserMgr.user = user;
    }

    public static User getUser() {
        return user;
    }
}
