package com.whk.game;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class GameGatewayService implements ApplicationListener<HeartbeatEvent> {

    private static final Logger logger = Logger.getLogger(GameGatewayService.class.getName());

    /**
     * 网关
     */
    private final ConcurrentHashMap<Integer, GameGatewayInfo> gameGatewayInfos = new ConcurrentHashMap<>();
    /**
     * 负载均衡用，与 gameGatewayInfos 一起变更
     */
    private int[] gameGateArr;

    /**
     * 玩家网关缓存
     */
    private final HashMap<Integer, LoadingCache<String, GameGatewayInfo>> cache = new HashMap<>();

    /**
     * 服务发现客户端实例
     */
    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 初始化  @PostConstruct 初始化注解，扫描完bean后初始化
     */
    @PostConstruct
    public void init() {
        refreshGameGatewayInfo();
    }

    /**
     * 刷新游戏服网关
     */
    private void refreshGameGatewayInfo() {
        var serverIns = discoveryClient.getInstances("game-gateway");
        gameGateArr = new int[serverIns.size()];
        int i = 0;
        serverIns.forEach(f -> {
            var hashcode = f.getInstanceId().hashCode();
            gameGateArr[i] = hashcode;
            var map = f.getMetadata();
            int zone = Integer.parseInt(f.getMetadata().getOrDefault("zone", "1"));
            gameGatewayInfos.put(hashcode, new GameGatewayInfo(hashcode, map.get("ip"), Integer.parseInt(map.get("port")),
                    f.getInstanceId(), zone));

            if (!cache.containsKey(zone)){
                LoadingCache<String, GameGatewayInfo> loadingCache = CacheBuilder.newBuilder().maximumSize(20000)
                        .expireAfterAccess(2, TimeUnit.HOURS).build(new CacheLoader<>() {
                            @Override
                            public GameGatewayInfo load(String id) {
                                var gate = selectGate(id);
                                if (gate.isEmpty()) {
                                    logger.warning("game's gate away void");
                                    return null;
                                } else {
                                    return selectGate(id).get();
                                }
                            }
                        });

                cache.put(zone, loadingCache);
            }
        });
    }

    /**
     * 选择游戏服网关
     *
     * @param id 玩家唯一id
     * @return Optional<GameGatewayInfo>
     */
    private Optional<GameGatewayInfo> selectGate(String id) {
        var map = gameGatewayInfos;
        if (map.size() != 0) {
            var hashCode = Math.abs(id.hashCode());
            var index = hashCode % map.size();
            return Optional.of(map.get(gameGateArr[index]));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 获取网关
     * @param id 玩家id
     * @param zone 大区
     * @return
     */
    public Optional<GameGatewayInfo> getGate(String id, int zone) throws ExecutionException {
        var zoneCache = cache.get(zone);
        var info = zoneCache.get(id);
        if (info != null){
            if (!gameGatewayInfos.containsKey(info.id)) {
                zoneCache.invalidate(id);
            }
        }
        return Optional.of(zoneCache.get(id));
    }


    @Override
    public void onApplicationEvent(HeartbeatEvent event) {
        refreshGameGatewayInfo();
    }


    public record GameGatewayInfo(int id, String ip, int port, String instanceId, int zone) {

    }
}
