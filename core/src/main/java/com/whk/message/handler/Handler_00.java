package com.whk.message.handler;

import com.whk.annotation.GameMessageHandler;

@GameMessageHandler
public class Handler_00 {
    //创建 SingleObject 的一个对象
    private static Handler_00 instance = new Handler_00();

    //让构造函数为 private，这样该类就不会被实例化
    private Handler_00(){}

    //获取唯一可用的对象
    public static Handler_00 getInstance(){
        return instance;
    }

    public void message_00(){



    }

    public void message_01(){



    }


}
