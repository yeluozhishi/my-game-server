package com.whk.game;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.whk.Exception.GameErrorException;
import com.whk.network_param.WebCenterError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class GameGatewayService implements ApplicationListener<HeartbeatEvent> {

    private static Logger logger = Logger.getLogger(GameGatewayService.class.getName());

    // 网关
    private ConcurrentHashMap<Integer, GameGatewayInfo> gameGatewayInfos = new ConcurrentHashMap<>();
    // 负载均衡用，与 gameGatewayInfos 一起变更
    private int[] gameGateArr;

    // 玩家网关缓存
    private LoadingCache<String, GameGatewayInfo> cache;

    // 服务发现客户端实例
    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 初始化
     * @PostConstruct 初始化注解，扫描完bean后初始化
     */
    @PostConstruct
    public void init(){
        this.refreshGameGatewayInfo();
        cache = CacheBuilder.newBuilder().maximumSize(20000)
                .expireAfterAccess(2, TimeUnit.HOURS).build(new CacheLoader<>() {
                    @Override
                    public GameGatewayInfo load(String s) {
                        var gate = selectGate(s);
                        if (gate.isEmpty()) {
                            throw new GameErrorException(WebCenterError.NOT_GAME_GATE, "game gate void");
                        }
                        return selectGate(s).get();
                    }
                });
    }


    /**
     * 刷新游戏服网关
     */
    private void refreshGameGatewayInfo(){
        var serverIns = discoveryClient.getInstances("game-gateway");
        gameGateArr = new int[serverIns.size()];
        int i = 0;
        serverIns.stream().forEach(f ->{
                    gameGateArr[i] = Integer.valueOf(f.getMetadata().get("id"));
                    gameGatewayInfos.put(Integer.valueOf(f.getMetadata().get("id")), new GameGatewayInfo(Integer.valueOf(f.getMetadata().get("id")), f.getHost(), f.getPort()));
                });
    }

    /**
     * 选择游戏服网关
     * @param id 玩家唯一id
     * @return
     */
    private Optional<GameGatewayInfo> selectGate(String id){
        var map = gameGatewayInfos;
        if (map != null && map.size() != 0){
            var hashCode = Math.abs(id.hashCode());
            var index = hashCode % map.size();
            return Optional.of(map.get(gameGateArr[index]));
        } else return Optional.empty();
    }

    public GameGatewayInfo getGate(String id) throws ExecutionException {
        var info = cache.get(id);
        if (info != null){
            if (!gameGatewayInfos.containsKey(info.id)){
                cache.invalidate(id);
            }
        }
        return cache.get(id);
    }


    @Override
    public void onApplicationEvent(HeartbeatEvent event) {
        this.refreshGameGatewayInfo();
    }


    public record GameGatewayInfo(int id, String ip, int port) {

    }
}
