package com.whk.service;

import com.whk.mongodb.Entity.UserAccount;
import com.whk.mongodb.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

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

    public Optional<UserAccount> register(String user_name, String pwd, HttpServletRequest request){

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
        userAccount.setUser_name(user_name);
        userAccount.setPassword(pwd);
        userAccount.setCreateTime(new Date().getTime());
        userAccount.setIp(ip);
        userAccountDao.saveOrUpdate(userAccount);

        return Optional.of(userAccount);
    }

    public void save(UserAccount userAccount) {
        userAccountDao.saveOrUpdate(userAccount);
    }
}
