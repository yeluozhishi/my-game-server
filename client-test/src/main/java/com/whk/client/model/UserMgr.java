package com.whk.client.model;

import lombok.Getter;

public class UserMgr {

    @Getter
    public static User user;

    public static void init(User user){
        UserMgr.user = user;
    }

}
