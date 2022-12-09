package com.whk.actor;

import com.whk.factory.PlayerFactory;
import com.whk.messageholder.SendMessageHolder;
import com.whk.mongodb.Entity.PlayerBase;
import com.whk.mongodb.Entity.UserAccount;
import com.whk.mongodb.dao.UserAccountDao;
import com.whk.net.channel.GameChannel;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.util.MessageI18n;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum PlayerMgr {
    // 实例
    INSTANCE;

    public final int MAX_PLAYER_NUM = 4;
    private UserAccountDao userAccountDao;

    private PlayerManager playerManager;

    PlayerMgr() {
        playerManager = new PlayerManager();
    }

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    private class PlayerManager {
        public Map<String, Player> playerMap = new ConcurrentHashMap<>();
    }

    /**
     * 玩家登录
     *
     * @param playerId       玩家id
     * @param gateInstanceId 网关实例id
     */
    public void playerLogin(String playerId, String gateInstanceId) {
        if (!playerManager.playerMap.containsKey(playerId)) {
            synchronized (playerManager) {
                playerManager.playerMap.put(playerId, PlayerFactory.buildPlayer(playerId, new GameChannel(), gateInstanceId));
            }
        }

    }

    /**
     * 获取玩家
     *
     * @param playerId 玩家id
     * @return
     */
    public Optional<Player> getPlayer(String playerId) {
        return Optional.ofNullable(playerManager.playerMap.get(playerId));
    }

    /**
     * 创建玩家
     *
     * @param userName       用户名
     * @param gateInstanceId 网关实例id
     */
    public void creatPlayer(String userName, String gateInstanceId) {
        // 检查角色
        var user = userAccountDao.findByUser(userName);
        user.ifPresentOrElse(u -> {
            Message msg = new Message(0x0001, "", new MapBean());
            if (u.getPlayers().size() >= MAX_PLAYER_NUM) {
                msg.setBody(MessageI18n.getMessage(14));
                SendMessageHolder.INSTANCE.sendMessage(msg, "");
                return;
            }
            var uid = UUID.randomUUID();
            var player = PlayerFactory.createPlayer(uid.toString(), gateInstanceId);
            playerLogin(player.id, gateInstanceId);
            var p = new PlayerBase(player.id, player.kind, player.sex, System.currentTimeMillis());
            u.addPlayer(p);
            userAccountDao.saveOrUpdate(u);
        }, () -> {
            UserAccount account = new UserAccount();
            account.setUser_name(userName);
            account.setPlayers(List.of());
            userAccountDao.saveOrUpdate(account);
        });
    }

}
