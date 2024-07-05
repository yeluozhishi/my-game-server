package com.whk.user;

import com.whk.Auth0JwtUtils;
import com.whk.message.Server;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Getter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理
 * user login: 服务器id token
 * get players: how get players?
 * player login: playerId
 *
 * @author Administrator
 */

@Getter
public enum UserMgr {
    // 实例
    INSTANCE;

    public final AttributeKey<Long> ATTR_USER_ID = AttributeKey.valueOf("userId");

    private final UserManager userManager;


    private KafkaMessageService kafkaMessageService;


    UserMgr() {
        userManager = new UserManager();
    }

    public void init(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    public void addUser(User user) {
        user.setKafkaMessageService(kafkaMessageService);
        user.setPassPort(true);
        userManager.userMap.put(user.getUserId(), user);
        user.getCtx().channel().attr(ATTR_USER_ID).set(user.getUserId());
    }

    /**
     * 获取用户 未检查
     *
     * @param userId 用户id
     */
    public User getUserByUserId(Long userId) {
        return userManager.userMap.get(userId);
    }


    public Optional<User> getUserByPlayerId(Long playerId) {
        return Optional.of(userManager.playerMap.get(playerId));
    }

    public void removeUser(Long userId) {
        var user = userManager.userMap.get(userId);
        if (user != null) {
            userManager.userMap.remove(user.getUserId());
            userManager.playerMap.remove(user.getServerInfo().getPlayerId());
            user.getCtx().close();
        }
    }

    /**
     * 设置基础消息信息
     *
     */
    public MessageWrapperProto.MessageWrapper WrapperMessage(MessageProto.Message message, Long userId) {
        var user = userManager.userMap.get(userId);
        var playerId = user == null ? 0L : user.getServerInfo().getPlayerId();
        return MessageWrapperProto.MessageWrapper.newBuilder()
                .setPlayerId(playerId)
                .setMessage(message).build();
    }

    public boolean passCheck(long userId) {
        return Optional.ofNullable(userManager.userMap.get(userId)).map(User::isPassPort).orElse(false);
    }

    private static class UserManager {
        public Map<Long, User> userMap = new ConcurrentHashMap<>();
        public Map<Long, User> playerMap = new ConcurrentHashMap<>();
    }


    public boolean playerLogin(Long userId, Long playerId) {
        var user = getUserByUserId(userId);
        if (user.getServerInfo().setPlayerId(playerId)) {
            userManager.playerMap.put(user.getServerInfo().getPlayerId(), user);
            return true;
        }
        return false;
    }

    public boolean containsUser(Long userId){
        return userManager.userMap.containsKey(userId);
    }

    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message) throws IOException {
        var user = getUserByPlayerId(message.getPlayerId());
        if (user.isPresent()){
            user.get().sendToServerMessage(message);
        }
    }

    public void sendToClientMessage(MessageWrapperProto.MessageWrapper message) {
        getUserByPlayerId(message.getPlayerId()).ifPresent(u -> u.sendToClientMessage(message.getMessage().toBuilder()));
    }

}
