package com.whk.user;

import com.whk.net.Message;
import com.whk.util.Auth0JwtUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理
 * @author Administrator
 */
public enum UserMgr {
    // 实例
    INSTANCE;

    final AttributeKey ATTR_USERNAME = AttributeKey.<String>valueOf("username");
    final AttributeKey ATTR_SERVER_ID = AttributeKey.<String>valueOf("serverId");

    private final UserManager userManager;

    UserMgr(){
        userManager = new UserManager();
    }

    public void addUser(User user){
        userManager.userMap.put(user.getUserId(), user);
        user.getChannel().attr(ATTR_USERNAME).set(user.getUserId());
    }

    public Optional<User> getUser(String userName){
        return Optional.ofNullable(userManager.userMap.get(userName));
    }

    private class UserManager{
        public Map<String, User> userMap = new ConcurrentHashMap<>();
        public Map<String, User> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录网关
     * @param message
     * @param channel
     */
    public void userLogin(Message message, Channel channel){
        if (message.getCommand() == 0){
            var body = message.getBody();
            var token = body.getString("token");
            if (Auth0JwtUtils.verify(token)){
                User user = new User(body.getString(""), message.getServerId(), channel);
                addUser(user);
            } else {
                channel.closeFuture();
            }
        }
    }

}
