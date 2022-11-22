package com.whk.user;

import com.whk.config.GatewayServerConfig;
import com.whk.net.channel.GameChannel;
import com.whk.net.channel.GameChannelInitializer;
import com.whk.net.channel.GameMessageEventDispatchService;
import com.whk.net.concurrent.GameEventExecutorGroup;
import com.whk.net.enity.Message;
import com.whk.service.ServerConnector;
import com.whk.util.Auth0JwtUtils;
import com.whk.util.SpringUtil;
import io.netty.channel.Channel;
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

    private ServerConnector serverConnector;


    private EventExecutorGroup rpcWorkerGroup = new DefaultEventExecutorGroup(2);

    UserMgr(){
        userManager = new UserManager();
    }

    public void init(ApplicationContext context, GameEventExecutorGroup workerGroup, GatewayServerConfig config,
                     GameChannelInitializer channelInitializer){
        this.config = config;
        service = new GameMessageEventDispatchService(workerGroup, channelInitializer, context);
        serverConnector = SpringUtil.getAppContext().getBean(ServerConnector.class);
    }

    public void addUser(User user){
        userManager.userMap.put(user.getUserId(), user);
        user.getChannel().attr(ATTR_USERNAME).set(user.getUserId());
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
     * @param channel
     */
    public void userLogin(Message message, Channel channel, KafkaTemplate<String, byte[]> kafkaTemplate){
        if (message.getCommand() == 0){
            var body = message.getBody();
            var token = body.getString("token");
            if (Auth0JwtUtils.verify(token)){
                var userId = body.getString("userId");
                var serverId = body.getInt("serverId");
                var gameChannel = new GameChannel();
                gameChannel.init("", config.getKafkaConfig().getServer(), serverId, 0,
                        service.getWorkerGroup().select(userId), service.getChannelInitializer(), kafkaTemplate);
                User user = new User(userId, serverId, 0, channel, gameChannel);
                addUser(user);
            } else {
                channel.closeFuture();
            }
        }
    }

    public void playerLogin(String playerId, String userId){
        var user = getUserByPlayerId(userId);
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
