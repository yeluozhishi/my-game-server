package com.whk.rest;

import com.whk.mongodb.Entity.AdminAccount;
import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.dao.AdminAccountDao;
import com.whk.network_param.ResponseEntity;
import com.whk.util.Auth0JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<Map> login(@RequestParam() String userName, @RequestParam() String password) {
        var admin = adminAccountDao.findByUserPWD(userName, password);
        if (admin.isEmpty()){
            AdminAccount adminAccount = new AdminAccount(userName, System.currentTimeMillis(), password);
            adminAccountDao.saveOrUpdate(adminAccount);
        }
        String token = Auth0JwtUtils.sign(Map.of("user_name", userName, "password", password));
        return new ResponseEntity<>(Map.of("userName", userName, "token", token));
    }


}
