package com.whk.controller;

import com.whk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.whk.Auth0JwtUtils;
import com.whk.message.MapBean;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public MapBean login(@RequestBody Map<String, String> map) {
        var userName = map.get("userName");
        var password = map.get("pwd");
        adminService.login(userName, password);
        String token = Auth0JwtUtils.sign(Map.of("userName", userName, "password", password));
        assert token != null;
        return new MapBean(Map.of("userName", userName, "token", token));
    }

}
