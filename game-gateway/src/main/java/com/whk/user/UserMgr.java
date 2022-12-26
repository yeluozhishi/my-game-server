package com.whk.user;

import com.whk.config.GatewayServerConfig;
import com.whk.net.channel.GameChannel;
import com.whk.net.channel.GameChannelInitializer;
import com.whk.net.channel.GameMessageEventDispatchService;
import com.whk.net.concurrent.GameEventExecutorGroup;
import com.whk.net.enity.Message;
import com.whk.serverinfo.Server;
import com.whk.util.Auth0JwtUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

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

    private GameMessageEventDispatchService service;

    private GatewayServerConfig config;

    private final UserManager userManager;

    private KafkaTemplate<String, byte[]> kafkaTemplate;


    private EventExecutorGroup rpcWorkerGroup = new DefaultEventExecutorGroup(2);

    UserMgr(){
        userManager = new UserManager();
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate, ApplicationContext context, GameEventExecutorGroup workerGroup,
                     GatewayServerConfig config, GameChannelInitializer channelInitializer){
        this.config = config;
        service = new GameMessageEventDispatchService(workerGroup, channelInitializer, context);
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addUser(User user){
        userManager.userMap.put(user.getUserId(), user);
        user.getCtx().channel().attr(ATTR_USERNAME).set(user.getUserId());
    }

    /**
     * 获取用户 未检查
     * @param userName
     * @return
     */
    public Optional<User> getUserByUsernameWithoutCheck(String userName){
        return Optional.ofNullable(userManager.userMap.get(userName));
    }

    public Optional<User> getUserByUsername(String userName){
        var user = userManager.userMap.get(userName);
        if (user != null && user.isCompleted()){
            return Optional.ofNullable(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPlayerId(String playerId){
        var user = userManager.playerMap.get(playerId);
        if (user != null){
            return Optional.ofNullable(user);
        } else {
            return Optional.empty();
        }
    }

    public void removeUser(String userId) {
        var user = userManager.userMap.get(userId);
        if (user != null){
            userManager.userMap.remove(user.getUserId());
            if (user.isCompleted()){
                userManager.playerMap.remove(user.getPlayerId());
            }
        }
    }

    private class UserManager{
        public Map<String, User> userMap = new ConcurrentHashMap<>();
        public Map<String, User> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 用户登录网关
     * @param message
     * @param ctx
     */
    public void userLogin(Message message, ChannelHandlerContext ctx, Map<Integer, Server> serverMap){
        if (message.getCommand() == 0){
            var body = message.getBody();
            var token = body.getString("token");
            if (Auth0JwtUtils.verify(token)){
                var userName = body.getString("userName");
                if (userManager.userMap.containsKey(userName)){
                    removeUser(userName);
                }
                var serverId = body.getInt("serverId");
                var gameChannel = new GameChannel();
                var server = serverMap.get(serverId);
                if (server != null) {
                    gameChannel.init(server.getInstanceId(), serverId, 0,
                            service.getWorkerGroup().select(userName), service.getChannelInitializer(), kafkaTemplate);
                    User user = new User(userName, ctx, gameChannel);
                    addUser(user);
                }
            } else {
                ctx.close();
            }
        }
    }

    public void playerLogin(String playerId, String userName){
        var user = getUserByUsernameWithoutCheck(userName);
        user.ifPresent(value -> {
            value.setPlayerId(playerId);
            userManager.playerMap.put(value.getPlayerId(), value);
            value.completed();
        });
    }

    public void sendToServerMessage(Message message){
        var user = getUserByPlayerId(message.getPlayerId());
        user.ifPresent(u -> u.sendToServerMessage(message));
    }

    public void sendToClientMessage(String playerId, Message message){
        var user = getUserByPlayerId(message.getPlayerId());
        user.ifPresent(u -> u.sendToClientMessage(message));
    }

}
