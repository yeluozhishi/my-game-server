package com.whk.service;

import com.whk.mongodb.Entity.PlayerBase;
import com.whk.mongodb.Entity.UserAccount;
import com.whk.mongodb.dao.UserAccountDao;
import com.whk.network_param.MapBean;
import com.whk.util.MessageI18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    /** 最大创建角色数 */
    public final int MAX_PLAYER_NUM = 4;

    private UserAccountDao userAccountDao;

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    public Optional<UserAccount> login(String userName, String pwd){
        return userAccountDao.findByUserPWD(userName, pwd);
    }

    public Optional<UserAccount> login(String openId){
        return userAccountDao.findByOpenId(openId);
    }

    public Optional<UserAccount> register(String userName, String pwd, HttpServletRequest request){

        String ip = request.getRemoteAddr();
        if(null==ip||"127.0.0.1".equals(ip)){
            // 获取请求主机IP地址,如果通过代理进来&#xff0c;则透过防火墙获取真实IP地址
            ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
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

        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(userName);
        userAccount.setPassword(pwd);
        userAccount.setCreateTime(System.currentTimeMillis());
        userAccount.setIp(ip);
        userAccountDao.saveOrUpdate(userAccount);

        return Optional.of(userAccount);
    }

    public MapBean createPlayer(String userName, int kind, int sex){
        var userAccount = userAccountDao.findById(userName);
        MapBean mapBean = new MapBean();
        userAccount.ifPresentOrElse(f -> {
            var size = 0;
            if (f.getPlayerBases() != null) size = f.getPlayerBases().size();
            if (size < MAX_PLAYER_NUM){
                PlayerBase playerBase = new PlayerBase();
                var pid = UUID.randomUUID().toString();
                playerBase.setId(pid);
                playerBase.setSex(sex);
                playerBase.setKind(kind);
                playerBase.setLastLogin(System.currentTimeMillis());
                if (f.getPlayerBases() != null){
                    f.getPlayerBases().add(playerBase);
                } else {
                    f.setPlayerBases(List.of(playerBase));
                }

                userAccountDao.saveOrUpdate(f);
                mapBean.putAll(Map.of("pid", pid));
            } else {
                mapBean.putAll(MessageI18n.getMessage(14));
            }
        }, () -> {
            mapBean.putAll(MessageI18n.getMessage(15));
        });
        return mapBean;
    }
}
