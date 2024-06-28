package com.whk.user;

import com.whk.config.GatewayServerConfig;
import com.whk.net.kafka.KafkaMessageService;
import org.whk.message.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.whk.Auth0JwtUtils;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public enum UserMgr {
    // 实例
    INSTANCE;

    public final AttributeKey<Long> ATTR_USER_ID = AttributeKey.<Long>valueOf("userId");

    private GatewayServerConfig config;

    private final UserManager userManager;


    private KafkaMessageService kafkaMessageService;


    UserMgr() {
        userManager = new UserManager();
    }

    public void init(GatewayServerConfig config, KafkaMessageService kafkaMessageService) {
        this.config = config;
        this.kafkaMessageService = kafkaMessageService;
    }

    public void addUser(User user) {
        userManager.userMap.put(user.getUserId(), user);
        user.getCtx().channel().attr(ATTR_USER_ID).set(user.getUserId());
    }

    /**
     * 获取用户 未检查
     *
     * @param userId 用户id
     * @return
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
        }
    }

    /**
     * 设置基础消息信息
     *
     * @param message
     * @param userId
     * @return
     */
    public MessageWrapperProto.MessageWrapper WrapperMessage(MessageProto.Message message, Long userId) {
        var user = userManager.userMap.get(userId);
        var playerId = user == null ? 0L : user.getServerInfo().getPlayerId();
        return MessageWrapperProto.MessageWrapper.newBuilder()
                .setPlayerId(playerId)
                .setMessage(message).build();
    }

    public boolean passCheck(long userId) {
        return Optional.of(userManager.userMap.get(userId)).map(User::isPassPort).orElse(false);
    }

    private static class UserManager {
        public Map<Long, User> userMap = new ConcurrentHashMap<>();
        public Map<Long, User> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录网关
     *
     * @param message
     * @param ctx
     */
    public void userLogin(MessageProto.Message message, ChannelHandlerContext ctx, Map<Integer, Server> serverMap) throws UnsupportedEncodingException {
        if (message.getCommand() == 0) {
            var body = message.getLoginReq();
            var token = body.getToken();
            if (Auth0JwtUtils.verify(token)) {
                var userId = Auth0JwtUtils.getClaims(token).get("userId").asLong();
                if (userManager.userMap.containsKey(userId)) {
                    removeUser(userId);
                }
                var serverId = body.getServerId();

                var server = serverMap.get(serverId);
                if (server != null) {
                    PlayerServerInfo serverInfo = new PlayerServerInfo(server, server, 0L);
                    User user = new User(userId, ctx, serverInfo, kafkaMessageService, true);
                    addUser(user);
                }
            } else {
                ctx.close();
            }
        }
    }

    public void playerLogin(Long playerId) {
        getUserByPlayerId(playerId).ifPresent(value -> {
            value.getServerInfo().setPlayerId(playerId);
            userManager.playerMap.put(value.getServerInfo().getPlayerId(), value);
            value.sendTips(18);
        });
    }

    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message) {
        getUserByPlayerId(message.getPlayerId()).ifPresent(u -> {
            try {
                u.sendToServerMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendToClientMessage(MessageWrapperProto.MessageWrapper message) {
        getUserByPlayerId(message.getPlayerId()).ifPresent(u -> u.sendToClientMessage(message.getMessage()));
    }

}
