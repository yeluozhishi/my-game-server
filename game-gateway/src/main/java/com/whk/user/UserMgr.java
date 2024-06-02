package com.whk.user;

import com.whk.MessageI18n;
import com.whk.config.GatewayServerConfig;
import com.whk.net.channel.GameChannel;
import com.whk.net.channel.GameChannelInitializer;
import com.whk.net.channel.GameMessageEventDispatchService;
import com.whk.net.concurrent.GameEventExecutorGroup;
import com.whk.serverinfo.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.springframework.kafka.core.KafkaTemplate;
import org.whk.Auth0JwtUtils;
import org.whk.TipsConvert;
import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理
 *
 * @author Administrator
 */
public enum UserMgr {
    // 实例
    INSTANCE;

    public final AttributeKey ATTR_USER_ID = AttributeKey.<Long>valueOf("userId");
    public final AttributeKey ATTR_SERVER_ID = AttributeKey.<String>valueOf("serverId");

    private GameMessageEventDispatchService service;

    private GatewayServerConfig config;

    private final UserManager userManager;

    private KafkaTemplate<String, byte[]> kafkaTemplate;


    UserMgr() {
        userManager = new UserManager();
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate, GameEventExecutorGroup workerGroup,
                     GatewayServerConfig config, GameChannelInitializer channelInitializer) {
        this.config = config;
        service = new GameMessageEventDispatchService(workerGroup, channelInitializer);
        this.kafkaTemplate = kafkaTemplate;
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
    public Optional<User> getUserByUserIdWithoutCheck(Long userId) {
        return Optional.ofNullable(userManager.userMap.get(userId));
    }

    public Optional<User> getUserByPlayerId(Long playerId) {
        var user = userManager.playerMap.get(playerId);
        if (user != null) {
            return Optional.ofNullable(user);
        } else {
            return Optional.empty();
        }
    }

    public void removeUser(Long userId) {
        var user = userManager.userMap.get(userId);
        if (user != null) {
            userManager.userMap.remove(user.getUserId());
            if (user.isCompleted()) {
                userManager.playerMap.remove(user.getPlayerId());
            }
        }
    }

    /**
     * 设置基础消息信息
     *
     * @param message
     * @param userId
     * @return
     */
    public MessageWrapperOuterClass.MessageWrapper WrapperMessage(MessageOuterClass.Message message, Long userId) {
        var user = userManager.userMap.get(userId);
        var playerId = user == null ? 0L : user.getPlayerId();
        return MessageWrapperOuterClass.MessageWrapper.newBuilder()
                .setPlayerId(playerId).setUserId(userId).setServerInstance(config.getInstanceId())
                .setMessage(message).build();
    }

    private static class UserManager {
        public Map<Long, User> userMap = new ConcurrentHashMap<>();
        public Map<Long, User> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录网关
     *  @param message
     * @param ctx
     */
    public void userLogin(MessageWrapperOuterClass.MessageWrapper message, ChannelHandlerContext ctx, Map<Integer, Server> serverMap) throws UnsupportedEncodingException {
        if (message.getMessage().getCommand() == 0) {
            var body = message.getMessage().getLoginReq();
            var token = body.getToken();
            if (Auth0JwtUtils.verify(token)) {
                var userId = Auth0JwtUtils.getClaims(token).get("userId").asLong();
                if (userManager.userMap.containsKey(userId)) {
                    removeUser(userId);
                }
                var serverId = body.getServerId();
                var gameChannel = new GameChannel();
                var server = serverMap.get(serverId);
                if (server != null) {
                    gameChannel.init(server.getInstanceId(), serverId, 0,
                            service.getWorkerGroup().select(userId), service.getChannelInitializer(), kafkaTemplate);
                    User user = new User(userId, ctx, gameChannel);
                    addUser(user);
                }
            } else {
                ctx.close();
            }
        }
    }

    public void playerLogin(Long playerId) {
        getUserByPlayerId(playerId).ifPresent(value -> {
            value.setPlayerId(playerId);
            userManager.playerMap.put(value.getPlayerId(), value);
            value.completed();
            var msg = MessageOuterClass.Message.newBuilder();
            msg.setCommand(0x0001);
            msg.setTips(TipsConvert.convert(MessageI18n.getMessageTuple(18)));
            value.sendToClientMessage(msg.build());
        });
    }

    public void sendToServerMessage(MessageWrapperOuterClass.MessageWrapper message) {
        getUserByPlayerId(message.getPlayerId()).ifPresent(u -> u.sendToServerMessage(message));
    }

    public void sendToClientMessage(MessageWrapperOuterClass.MessageWrapper message) {
        getUserByPlayerId(message.getPlayerId()).ifPresent(u -> u.sendToClientMessage(message.getMessage()));
    }

}
