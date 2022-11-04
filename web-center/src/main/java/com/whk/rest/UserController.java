package com.whk.rest;

import com.whk.game.GameGatewayService;
import com.whk.mongodb.Entity.UserAccount;
import com.whk.network_param.IServerError;
import com.whk.network_param.MapBean;
import com.whk.network_param.WebCenterError;
import com.whk.result.LoginResult;
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
        String userName = map.getOrDefault("user_name", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        int zone = Integer.parseInt(map.getOrDefault("zone", "1"));
        Optional<UserAccount> userAccount;
        LoginResult loginResult;
        if (!openId.isBlank()){
            userAccount = userService.login(openId);
            userName = openId;
        } else {
            userAccount = userService.login(userName, pwd);
        }

        if (userAccount.isPresent()){
            loginResult = new LoginResult();
            loginResult.setId(userAccount.get().getUserName());
            String token = Auth0JwtUtils.sign(Map.of("user_name", userName));
            loginResult.setToken(token);
            Optional<GameGatewayService.GameGatewayInfo> gate =
                    gameGatewayService.getGate(userAccount.get().getUserName(), zone);
            if (gate.isEmpty()){
                logger.warning("zone不存在：" + zone);
                return new MapBean(WebCenterError.ZONE_EMPTY);
            } else {
                loginResult.setGameGatewayInfo(gate.get());
            }
            logger.info("login success user_name：" + userName);
            return new MapBean(loginResult.toMap());
        } else {
            logger.info("login false user_name：" + userName);
            return new MapBean(WebCenterError.LOGIN_ERROR);
        }
    }

    @RequestMapping(value = "register")
    public MapBean register(HttpServletRequest request, @RequestBody Map<String, String> map) throws ExecutionException {
        String userName = map.getOrDefault("user_name", "");
        String pwd = map.getOrDefault("pwd", "");
        String openId = map.getOrDefault("openId", "");
        int zone = Integer.parseInt(map.getOrDefault("openId", ""));
        Optional<UserAccount> userAccount;
        LoginResult loginResult;
        if (openId != null && !openId.isBlank()){
            userAccount = userService.login(openId);
            userName = openId;
            pwd = "";
        } else {
            userAccount = userService.login(userName, pwd);
        }

        if (userAccount.isPresent()){
            logger.info("register false  user_name：" + userName);
            return new MapBean(WebCenterError.EXIST_ACCOUNT);

        } else {
            userAccount = userService.register(userName, pwd, request);
            if (userAccount.isPresent()){
                loginResult = new LoginResult();
                loginResult.setId(userAccount.get().getUserName());
                String token = Auth0JwtUtils.sign(Map.of("user_name", userName, "pwd", pwd));
                loginResult.setToken(token);
                Optional<GameGatewayService.GameGatewayInfo> gate =
                        gameGatewayService.getGate(userAccount.get().getUserName(), zone);
                if (gate.isEmpty()){
                    logger.warning("zone不存在：" + zone);
                } else {
                    loginResult.setGameGatewayInfo(gate.get());
                }
                logger.info("register success user_name：" + userName);
                return new MapBean(loginResult.toMap());
            } else {
                return new MapBean(WebCenterError.UNKNOWN);
            }

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
                gameGatewayService.getGate(GsonUtil.INSTANCE.GsonToBean(load, Map.class).get("user_name").toString(), zone);
        if (gate.isEmpty()){
            return new MapBean(WebCenterError.NOT_GAME_GATE);
        }
        return new MapBean(Map.of("gate", gate.get()));
    }


}
