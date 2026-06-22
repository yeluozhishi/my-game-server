package com.whk.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.whk.Auth0JwtUtils;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import com.whk.db.entity.UserAccountEntity;
import com.whk.game.GameGatewayService;
import com.whk.message.MapBean;
import com.whk.message.gamegate.PlayerEntityMessage;
import com.whk.message.gamegate.ReqPlayerListMessage;
import com.whk.result.LoginResult;
import com.whk.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

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
            loginResult.setUserId(userAccount.get().getId());
            String token = Auth0JwtUtils.sign(Map.of("userName", userName, "userId", userAccount.get().getId()));
            loginResult.setToken(token);
            Optional<GameGatewayService.GameGatewayInfo> gate =
                    gameGatewayService.getGate(userAccount.get().getUserName(), zone);
            if (gate.isEmpty()){
                log.warn("zone不存在：" + zone);
                return MessageI18n.getMessageMapBean(MESSAGE_CODE.大区不存在);
            } else {
                loginResult.setGameGatewayInfo(gate.get());
            }
            log.info("login success userName：" + userName);
            return new MapBean(loginResult.toMap());
        } else {
            log.info("login false userName：" + userName);
            return MessageI18n.getMessageMapBean(MESSAGE_CODE.用户名或密码错误);
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
            log.info("register false  userName：" + userName);
            return MessageI18n.getMessageMapBean(MESSAGE_CODE.用户已存在);
        } else {
            var user = userService.register(userName, pwd, request);
            loginResult = new LoginResult();
            loginResult.setUserId(user.getId());
            String token = Auth0JwtUtils.sign(Map.of("userName", userName, "pwd", pwd));
            loginResult.setToken(token);
            Optional<GameGatewayService.GameGatewayInfo> gate =
                    gameGatewayService.getGate(user.getUserName(), zone);
            if (gate.isEmpty()){
                log.warn("zone不存在：" + zone);
            } else {
                loginResult.setGameGatewayInfo(gate.get());
            }
            log.info("register success userName：" + userName);
            return new MapBean(loginResult.toMap());
        }
    }

    @RequestMapping(value = "getSomething")
    public MapBean getSomething(@RequestBody Map<String, String> map) {
        for (String q :
                map.values()) {
            log.info(q);
        }
        return new MapBean(Map.of("num", 2, "info", "you got something"));
    }

    @RequestMapping(value = "getGameGateway")
    public MapBean getGameGateway(@RequestBody Map<String, String> map) throws ExecutionException {
        String load = Auth0JwtUtils.getPayloadByBase64(map.get("token"));
        int zone = Integer.parseInt(map.getOrDefault("zone", "1"));
        Optional<GameGatewayService.GameGatewayInfo> gate =
                gameGatewayService.getGate(JSONUtil.toBean(load, Map.class).get("userName").toString(), zone);
        return gate.map(gameGatewayInfo -> new MapBean(Map.of("gate", gameGatewayInfo))).orElseGet(() -> MessageI18n.getMessageMapBean(MESSAGE_CODE.没有游戏网关信息));
    }

    @RequestMapping(value = "createPlayer")
    public MapBean createPlayer(@RequestBody Map<String, String> map) {
        Long userId = Long.parseLong(map.getOrDefault("userId", "0"));
        int sex = Integer.parseInt(map.getOrDefault("sex", "0"));
        int kind = Integer.parseInt(map.getOrDefault("kind", "0"));
        long playerId = Long.parseLong(map.getOrDefault("playerId", "0"));
        long serverId = Long.parseLong(map.getOrDefault("serverId", "0"));
        String name = map.getOrDefault("name", "name");
        return userService.createPlayer(userId, kind, sex, playerId, serverId, name);
    }

    @RequestMapping(value = "getPlayers")
    public List<PlayerEntityMessage> getPlayer(@RequestBody ReqPlayerListMessage message) {
        var list = userService.getPlayers(message.getUserId(), message.getServerId());
        return list.stream().map(playerEntity -> BeanUtil.copyProperties(playerEntity, PlayerEntityMessage.class))
                .collect(Collectors.toList());
    }
}
