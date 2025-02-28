package com.whk.client.component;

import com.whk.SpringUtils;
import com.whk.threadpool.processor.PlayerProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class InputThread {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void matchOrder(String[] command){
        var bean = SpringUtils.getBean(GameClientCommand.class);
        switch (command[0]){
            case "1" -> bean.connectServer();
            case "2" -> bean.choseServer(1);
            case "3" -> bean.getPlayers();
            case "4" -> bean.testMessage();
            case "5" -> bean.chosePlayer(1L);
            case "6" -> bean.enterScene("2071_1");
            case "7" -> bean.levelUp();
            case "8" -> bean.releaseSkill();
            case "9" -> bean.sceneMessage();

        }
    }

    public void start(){
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerProcessor());
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            log.info("输入编号：");
            while (scanner.hasNextLine()){
                var line = scanner.nextLine();
                var command = line.split(" ");
                try {
                    matchOrder(command);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}


