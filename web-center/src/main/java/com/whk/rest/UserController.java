package com.whk.rest;

import com.whk.MessageI18n;
import com.whk.db.entity.UserAccountEntity;
import com.whk.game.GameGatewayService;
import com.whk.result.LoginResult;
import com.whk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.whk.Auth0JwtUtils;
import org.whk.GsonUtil;
import org.whk.message.MapBean;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

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
    public MapBean login(@RequestBody Map<String, String> map) throws ExecutionException {
        String userName = map.getOrDefault("userName", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        int zone = Integer.parseInt(map.getOrDefault("zone", "1"));
        Optional<UserAccountEntity> userAccount;
        LoginResult loginResult;
        if (!openId.isBlank()){
            userAccount = userService.login(openId);
            userName = openId;
        } else {
            userAccount = userService.login(userName, pwd);
        }

        if (userAccount.isPresent()){
            loginResult = new LoginResult();
            loginResult.setUserId(userAccount.get().getUserId());
            String token = Auth0JwtUtils.sign(Map.of("userName", userName, "userId", userAccount.get().getUserId()));
            loginResult.setToken(token);
            Optional<GameGatewayService.GameGatewayInfo> gate =
                    gameGatewayService.getGate(userAccount.get().getUserName(), zone);
            if (gate.isEmpty()){
                logger.warning("zone不存在：" + zone);
                return MessageI18n.getMessage(8);
            } else {
                loginResult.setGameGatewayInfo(gate.get());
            }
            logger.info("login success userName：" + userName);
            return new MapBean(loginResult.toMap());
        } else {
            logger.info("login false userName：" + userName);
            return MessageI18n.getMessage(2);
        }
    }

    @RequestMapping(value = "register")
    public MapBean register(HttpServletRequest request, @RequestBody Map<String, String> map) throws ExecutionException {
        String userName = map.getOrDefault("userName", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        int zone = Integer.parseInt(map.getOrDefault("zone", ""));
        Optional<UserAccountEntity> userAccount;
        LoginResult loginResult;
        if (openId != null && !openId.isBlank()){
            userAccount = userService.login(openId);
            userName = openId;
            pwd = "";
        } else {
            userAccount = userService.login(userName, pwd);
        }

        if (userAccount.isPresent()){
            logger.info("register false  userName：" + userName);
            return MessageI18n.getMessage(3);
        } else {
            var user = userService.register(userName, pwd, request);
            loginResult = new LoginResult();
            loginResult.setUserId(user.getUserId());
            String token = Auth0JwtUtils.sign(Map.of("userName", userName, "pwd", pwd));
            loginResult.setToken(token);
            Optional<GameGatewayService.GameGatewayInfo> gate =
                    gameGatewayService.getGate(user.getUserName(), zone);
            if (gate.isEmpty()){
                logger.warning("zone不存在：" + zone);
            } else {
                loginResult.setGameGatewayInfo(gate.get());
            }
            logger.info("register success userName：" + userName);
            return new MapBean(loginResult.toMap());
        }
    }

    @RequestMapping(value = "getSomething")
    public MapBean getSomething(@RequestBody Map<String, String> map) {
        for (String q :
                map.values()) {
            System.out.println(q);
        }
        return new MapBean(Map.of("num", 2, "info", "you got something"));
    }

    @RequestMapping(value = "getGameGateway")
    public MapBean getGameGateway(@RequestBody Map<String, String> map) throws ExecutionException {
        String load = Auth0JwtUtils.getPayloadByBase64(map.get("token"));
        int zone = Integer.parseInt(map.getOrDefault("zone", "1"));
        Optional<GameGatewayService.GameGatewayInfo> gate =
                gameGatewayService.getGate(GsonUtil.INSTANCE.gsonToBean(load, Map.class).get("userName").toString(), zone);
        if (gate.isEmpty()){
            return MessageI18n.getMessage(4);
        }
        return new MapBean(Map.of("gate", gate.get()));
    }

    @RequestMapping(value = "createPlayer")
    public MapBean createPlayer(@RequestBody Map<String, String> map) {
        Long userId = Long.parseLong(map.getOrDefault("userId", "0"));
        int sex = Integer.parseInt(map.getOrDefault("sex", "0"));
        int kind = Integer.parseInt(map.getOrDefault("kind", "0"));
        return userService.createPlayer(userId, kind, sex);
    }
}
