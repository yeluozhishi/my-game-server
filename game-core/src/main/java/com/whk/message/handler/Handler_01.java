package com.whk.message.handler;

import com.whk.annotation.GameMessageHandler;

@GameMessageHandler
public class Handler_01 {

    //创建 SingleObject 的一个对象
    private static Handler_01 instance = new Handler_01();

    //让构造函数为 private，这样该类就不会被实例化
    private Handler_01(){}

    //获取唯一可用的对象
    public static Handler_01 getInstance(){
        return instance;
    }

    public void message_00(){
        System.out.println("Hello World!");
    }

    public void message_02(){
        System.out.println("Hello World!");
    }

    public void message_03(){
        System.out.println("Hello World!");
    }



}
