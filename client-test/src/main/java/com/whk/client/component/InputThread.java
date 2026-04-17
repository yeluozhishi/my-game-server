package com.whk.client.component;

import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.client.service.GameClientBoot;
import com.whk.threadpool.processor.PlayerProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Getter
public class InputThread {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private List<GameClientCommand> commands = new LinkedList<>();

    public static void matchOrder(String[] command, InputThread inputThread) {
        inputThread.getCommands().forEach(bean -> {
            switch (command[0]) {
                case "1" -> bean.connectServer();
                case "2" -> bean.choseServer(1);
                case "3" -> bean.getPlayers();
                case "4" -> bean.testMessage();
                case "5" -> bean.chosePlayer();
                case "6" -> bean.enterScene(2071, 1);
                case "7" -> bean.levelUp();
                case "8" -> bean.releaseSkill();
                case "9" -> bean.sceneMessage();
                case "10" -> bean.createPlayer();
            }
        });
    }

    public void start() {
        buildUser();
//        buildData("whk1", "123");
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerProcessor());
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            log.info("输入编号：");
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                var command = line.split(" ");
                try {
                    matchOrder(command, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void buildData(String userName, String pwd) {
        var user = new User(userName, pwd);
        var command = new GameClientCommand();
        GameClientConfig config = new GameClientConfig();
        command.setConfig(config);
        command.setBoot(new GameClientBoot(config));
        command.setUser(user);
        commands.add(command);
    }

    public void buildUser() {
        buildData("whk", "123");
        buildData("whk1", "123");
        buildData("whk2", "123");
        buildData("whk3", "123");
        buildData("whk4", "123");
        buildData("whk5", "123");
        buildData("whk6", "123");
        buildData("whk7", "123");
        buildData("whk8", "123");
        buildData("whk9", "123");
    }
}


