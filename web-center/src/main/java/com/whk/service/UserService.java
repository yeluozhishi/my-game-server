package com.whk.service;

import com.whk.MessageI18n;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.whk.centerdb.entity.PlayerInfoEntity;
import com.whk.centerdb.entity.UserAccountEntity;
import com.whk.centerdb.repository.PlayerInfoMapper;
import com.whk.centerdb.repository.UserAccountMapper;
import com.whk.message.MapBean;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    /**
     * 最大创建角色数
     */
    public final int MAX_PLAYER_NUM = 4;

    private UserAccountMapper userAccountMapper;

    private PlayerInfoMapper playerInfoMapper;

    @Autowired
    public void setUserAccountMapper(UserAccountMapper userAccountMapper) {
        this.userAccountMapper = userAccountMapper;
    }

    @Autowired
    public void setPlayerMapper(PlayerInfoMapper playerInfoMapper) {
        this.playerInfoMapper = playerInfoMapper;
    }

    public Optional<UserAccountEntity> login(String userName, String pwd) {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setUserName(userName);
        userAccount.setPassword(pwd);
        return userAccountMapper.findOne(Example.of(userAccount));
    }

    public Optional<UserAccountEntity> login(String openId) {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setOpenId(openId);
        return userAccountMapper.findOne(Example.of(userAccount));
    }

    public UserAccountEntity register(String userName, String pwd, HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        if (null == ip || "127.0.0.1".equals(ip)) {
            // 获取请求主机IP地址,如果通过代理进来&#xff0c;则透过防火墙获取真实IP地址
            ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (String s : ips) {
                    if (!("unknown".equalsIgnoreCase(s))) {
                        ip = s;
                        break;
                    }
                }
            }
        }

        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setUserName(userName);
        userAccount.setPassword(pwd);
        userAccount.setIp(ip);
        userAccount.setCreateTime(Instant.now());
        userAccountMapper.save(userAccount);
        return userAccountMapper.save(userAccount);
    }

    @Transactional
    public MapBean createPlayer(Long userId, int kind, int sex) {
        var playerInfo = new PlayerInfoEntity();
        playerInfo.setUserId(userId);
        MapBean mapBean = new MapBean();
        var players = playerInfoMapper.findAll(Example.of(playerInfo));
        if (players.size() > MAX_PLAYER_NUM){
            mapBean.putAll(MessageI18n.getMessage(14));
            return mapBean;
        }

        playerInfo.setSex((byte) sex);
        playerInfo.setCareer(kind);
        playerInfo.setLastLogin(System.currentTimeMillis());

        playerInfo = playerInfoMapper.save(playerInfo);
        mapBean.put("pid", playerInfo.getId());

        return mapBean;
    }

    public List<PlayerInfoEntity> getPlayers(long userId, int serverId) {
        var playerInfo = new PlayerInfoEntity();
        playerInfo.setUserId(userId);
        playerInfo.setServerId(serverId);
        return playerInfoMapper.findAll(Example.of(playerInfo));
    }
}
