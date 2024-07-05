package com.whk.client.component;

import com.whk.SpringUtils;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputThread {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void matchOrder(String[] command){
        var bean = SpringUtils.getBean(GameClientCommand.class);
        switch (command[0]){
            case "1" -> bean.connectServer();
            case "2" -> bean.choseServer(1);
            case "3" -> bean.getPlayers(1L);
            case "4" -> bean.testMessage(1L);
            case "5" -> bean.chosePlayer(1L);

        }



    }

    public void start(){
        executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入编号：");
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


