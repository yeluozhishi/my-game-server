package com.whk.rest;

import com.whk.game.GameGatewayService;
import com.whk.mongodb.Entity.UserAccount;
import com.whk.network_param.ResponseEntity;
import com.whk.network_param.WebCenterError;
import com.whk.result.LoginResult;
import com.whk.result.Something;
import com.whk.service.UserService;
import com.whk.util.Auth0JwtUtils;
import com.whk.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    private UserService userService;

    private GameGatewayService gameGatewayService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGameGatewayService(GameGatewayService gameGatewayService) {
        this.gameGatewayService = gameGatewayService;
    }

    @RequestMapping(value = "login")
    public ResponseEntity<LoginResult> login(HttpServletRequest request, @RequestBody Map<String, String> map) {
        String user_name = map.getOrDefault("user_name", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        Optional<UserAccount> userAccount;
        LoginResult loginResult;
        if (openId != null && !openId.isBlank()){
            userAccount = userService.login(openId);
            user_name = openId;
        } else {
            userAccount = userService.login(user_name, pwd);
        }

        if (userAccount.isPresent()){
            loginResult = new LoginResult();
            loginResult.setId(userAccount.get().getUser_name());
            String token = Auth0JwtUtils.sign(Map.of("user_name", user_name));
            loginResult.setToken(token);
            loginResult.setGate_ip("127.0.0.1");
            loginResult.setGate_port(6000);
            logger.info("login success user_name：" + user_name);
            return new ResponseEntity<>(loginResult);
        } else {
            logger.info("login false user_name：" + user_name);
            return new ResponseEntity<>(WebCenterError.LOGIN_ERROR);
        }
    }

    @RequestMapping(value = "register")
    public ResponseEntity<LoginResult> register(HttpServletRequest request, @RequestBody Map<String, String> map) {
        String user_name = map.getOrDefault("user_name", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        Optional<UserAccount> userAccount;
        LoginResult loginResult;
        if (openId != null && !openId.isBlank()){
            userAccount = userService.login(openId);
            user_name = openId;
            pwd = "";
        } else {
            userAccount = userService.login(user_name, pwd);
        }

        if (userAccount.isPresent()){
            logger.info("register false  user_name：" + user_name);
            return new ResponseEntity<>(WebCenterError.EXIST_ACCOUNT);
        } else {
            userAccount = userService.register(user_name, pwd, request);
            if (userAccount.isPresent()){
                loginResult = new LoginResult();
                loginResult.setId(userAccount.get().getUser_name());
                String token = Auth0JwtUtils.sign(Map.of("user_name", user_name, "pwd", pwd));
                loginResult.setToken(token);
                loginResult.setGate_ip("127.0.0.1");
                loginResult.setGate_port(6000);
                logger.info("register success user_name：" + user_name);
                return new ResponseEntity<>(loginResult);
            } else {
                return new ResponseEntity<>(WebCenterError.UNKNOWN);
            }

        }
    }

    @RequestMapping(value = "getSomething")
    public ResponseEntity<Something> getSomething(HttpServletRequest request, @RequestBody Map<String, String> map) {
        for (String q :
                map.values()) {
            System.out.println(q);
        }
        Something something = new Something();
        something.setInfo("you got something");
        something.setNum(2);
        return new ResponseEntity<>(something);
    }

    @RequestMapping(value = "getGameGateway")
    public ResponseEntity<GameGatewayService.GameGatewayInfo> getGameGateway(HttpServletRequest request, @RequestBody Map<String, String> map) throws ExecutionException {
        String load = Auth0JwtUtils.getPayloadByBase64(map.get("token"));
        GameGatewayService.GameGatewayInfo gate =
                gameGatewayService.getGate(GsonUtil.INSTANCE.GsonToBean(load, Map.class).get("user_name").toString());
        return new ResponseEntity<>(gate);
    }


}
