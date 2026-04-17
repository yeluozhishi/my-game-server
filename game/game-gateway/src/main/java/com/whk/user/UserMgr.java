package com.whk.user;

import com.whk.net.Session;
import com.whk.net.kafka.KafkaMessageConsumeService;
import io.netty.util.AttributeKey;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理
 * user login: 服务器id token
 * player login: playerId
 *
 * @author Administrator
 */

@Getter
public enum UserMgr {
    // 实例
    INSTANCE;

    public final AttributeKey<Session> SESSION = AttributeKey.valueOf("Session");

    private final UserManager userManager;


    private KafkaMessageConsumeService kafkaMessageConsumeService;


    UserMgr() {
        userManager = new UserManager();
    }

    public void init(KafkaMessageConsumeService kafkaMessageConsumeService) {
        this.kafkaMessageConsumeService = kafkaMessageConsumeService;
    }

    public void addUser(User user) {
        userManager.userMap.put(user.getUserId(), user);
        user.getSession().getHandler().channel().attr(SESSION).set(user.getSession());
    }

    /**
     * 获取用户 未检查
     *
     * @param userId 用户id
     */
    public User getUserByUserId(Long userId) {
        return userManager.userMap.get(userId);
    }


    public User getUserByPlayerId(Long playerId) {
        return userManager.playerMap.get(playerId);
    }

    public void logOut(Long userId) {
        var user = userManager.userMap.get(userId);
        if (user != null) {
            userManager.userMap.remove(user.getUserId());
            userManager.playerMap.remove(user.getServerInfo().getPlayerId());
            user.getSession().close();
        }
    }

    private static class UserManager {
        public Map<Long, User> userMap = new ConcurrentHashMap<>();
        public Map<Long, User> playerMap = new ConcurrentHashMap<>();
    }


    public boolean playerLogin(User user, Long playerId) {
        if (user.getServerInfo().setPlayerId(playerId)) {
            userManager.playerMap.put(user.getServerInfo().getPlayerId(), user);
            return false;
        }
        return true;
    }

    public boolean containsUser(Long userId) {
        return userManager.userMap.containsKey(userId);
    }

}
