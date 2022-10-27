package com.whk.user;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum UserMgr {
    INSTANCE;

    private final UserManager userManager;

    UserMgr(){
        userManager = new UserManager();
    }

    public void addUser(User user){
        userManager.userMap.put(user.getUserId(), user);
    }

    public Optional<User> getUser(String userName){
        return Optional.ofNullable(userManager.userMap.get(userName));
    }

    private static class UserManager{
        public Map<String, User> userMap = new ConcurrentHashMap<>();
    }

}
