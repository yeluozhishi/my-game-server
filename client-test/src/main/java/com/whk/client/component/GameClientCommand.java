package com.whk.client.component;

import com.google.protobuf.ByteString;
import com.whk.CmdToMessageUtil;
import com.whk.client.config.GameClientConfig;
import com.whk.client.model.UserMgr;
import com.whk.client.service.GameClientBoot;
import com.whk.client.service.GameClientInitService;
import com.whk.protobuf.message.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@Slf4j
public class GameClientCommand {

    private GameClientBoot boot;

    private GameClientConfig config;

    @Autowired
    public void setBoot(GameClientBoot boot) {
        this.boot = boot;
    }

    @Autowired
    public void setConfig(GameClientConfig config) {
        this.config = config;
    }

    public void sendMessage(Class<?> c, ByteString byteString) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(CmdToMessageUtil.getInstance().getCmd(c));
        msg.setPayload(byteString);
        boot.getChannel().writeAndFlush(msg);
    }

    @ShellMethod("连接服务器：connect-server")
    public void connectServer() {
        GameClientInitService initService = new GameClientInitService(config);
        initService.login();
        initService.showServerList();
        boot.launch();
    }

    @ShellMethod("选区：chose-server")
    public void choseServer(@ShellOption(defaultValue = "0") Integer serverId) {

        var user = UserMgr.getUser();
        // 选区，用户信息注册到网关，获取角色列表
        LoginProto.LoginReq message = LoginProto.LoginReq.newBuilder().setServerId(serverId)
                .setToken(user.getToken()).setUserId(user.getUserId()).build();

        log.info("choseServer");
        user.setServerId(serverId);
        sendMessage(message.getClass(), message.toByteString());
    }

    @ShellMethod("发送消息：send-message [msg]")
    public void chosePlayer(@ShellOption(defaultValue = "0") Long playerId) {
        var user = UserMgr.getUser();
        user.setPlayerId(playerId);
        PlayerInfoProto.ReqPlayerLogin message = PlayerInfoProto.ReqPlayerLogin.newBuilder().setPlayerId(playerId).build();
        sendMessage(message.getClass(), message.toByteString());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void testMessage() {
        var msg = PlayerInfoProto.TestMessage.newBuilder().setMsg("Hi! ").build();
        sendMessage(msg.getClass(), msg.toByteString());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void getPlayers() {
        PlayerInfoProto.ReqPlayers message = PlayerInfoProto.ReqPlayers.newBuilder().build();
        sendMessage(message.getClass(), message.toByteString());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void enterScene(String l) {
        var msg = SceneProto.ReqEnterScene.newBuilder().setSceneId(l).build();
        sendMessage(msg.getClass(), msg.toByteString());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void levelUp() {
        sendMessage(PlayerInfoProto.ReqLevelUp.class, PlayerInfoProto.ReqLevelUp.newBuilder().build().toByteString());
    }

    @ShellMethod("发送消息：send-message1 [msg]")
    public void releaseSkill() {
        sendMessage(SkillProto.ReqReleaseSkill.class, SkillProto.ReqReleaseSkill.newBuilder().setSkillId(7111).build().toByteString());
    }

    public void sceneMessage() {
        sendMessage(SceneProto.SceneMessage.class, SceneProto.SceneMessage.newBuilder().setDesc("7111").build().toByteString());
    }
}
