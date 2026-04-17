package com.whk.client.component;

import com.google.protobuf.Message;
import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
import com.whk.protobuf.message.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@Getter
@Setter
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    private User user;

    public void setBoot(GameClientBoot boot) {
        this.boot = boot;
    }

    public void setConfig(GameClientConfig config) {
        this.config = config;
    }

    private void sendMessage(Message message) {
        boot.getChannel().writeAndFlush(message);
    }

    @ShellMethod("连接服务器：connect-server")
    public void connectServer() {
        GameClientInitService initService = new GameClientInitService(config);
        if (!initService.login(user)) return;
        initService.showServerList();
        boot.launch(user);
    }

    @ShellMethod("选区：chose-server")
    public void choseServer(@ShellOption(defaultValue = "0") Integer serverId) {
        // 选区，用户信息注册到网关，获取角色列表
        LoginProto.ReqLogin message = LoginProto.ReqLogin.newBuilder().setServerId(serverId)
                .setToken(user.getToken()).setUserId(user.getUserId()).build();

        log.info("choseServer");
        user.setServerId(serverId);
        sendMessage(message);
    }

    @ShellMethod("发送消息：send-message [msg]")
    public void chosePlayer() {
        PlayerInfoProto.ReqPlayerLogin message = PlayerInfoProto.ReqPlayerLogin.newBuilder().setPlayerId(user.getPlayerId()).build();
        sendMessage(message);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void testMessage() {
        var msg = PlayerInfoProto.TestMessage.newBuilder().setMsg("Hi! ").build();
        sendMessage(msg);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void getPlayers() {
        PlayerInfoProto.ReqPlayers message = PlayerInfoProto.ReqPlayers.newBuilder().build();
        sendMessage(message);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void enterScene(int mapId, int line) {
        var msg = SceneProto.ReqEnterScene.newBuilder().setMapId(mapId).setLine(line).build();
        sendMessage(msg);
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void levelUp() {
        sendMessage(PlayerInfoProto.ReqLevelUp.newBuilder().build());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void releaseSkill() {
        SkillProto.ReqReleaseSkill.Builder builder = SkillProto.ReqReleaseSkill.newBuilder().setSkillId(7111);
        sendMessage(builder.build());
    }

    public void sceneMessage() {
        SceneProto.SceneMessage.Builder builder = SceneProto.SceneMessage.newBuilder().setDesc("7111");
        sendMessage(builder.build());
    }

    public void createPlayer() {
        CreatePlayerProto.CreatePlayer.Builder builder = CreatePlayerProto.CreatePlayer.newBuilder()
                .setName(user.getUserName())
                .setSex(1)
                .setCareer(1)
                .setServerId(1)
                .setUserId(user.getUserId());
        sendMessage(builder.build());
    }
}
