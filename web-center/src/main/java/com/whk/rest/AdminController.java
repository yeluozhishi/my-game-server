package com.whk.rest;

import com.whk.mongodb.Entity.AdminAccount;
import com.whk.mongodb.dao.AdminAccountDao;
import com.whk.network_param.MapBean;
import com.whk.util.Auth0JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminAccountDao adminAccountDao;

    @Autowired
    public void setAdminAccountDao(AdminAccountDao adminAccountDao) {
        this.adminAccountDao = adminAccountDao;
    }

    @PostMapping("/login")
    public MapBean login(@RequestBody Map<String, String> map) {
        var userName = map.get("userName");
        var password = map.get("pwd");
        var admin = adminAccountDao.findByUserPWD(userName, password);
        if (admin.isEmpty()){
            AdminAccount adminAccount = new AdminAccount(userName, System.currentTimeMillis(), password);
            adminAccountDao.saveOrUpdate(adminAccount);
        }
        String token = Auth0JwtUtils.sign(Map.of("userName", userName, "password", password));
        assert token != null;
        return new MapBean(Map.of("userName", userName, "token", token));
    }

}
