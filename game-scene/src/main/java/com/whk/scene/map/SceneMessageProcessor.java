package com.whk.scene.map;

import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.processor.AbstractMessageProcessor;

import java.util.Objects;

public class SceneMessageProcessor extends AbstractMessageProcessor {

    @Override
    public void message(AbstractHandler handler) {
       var scene = SceneManager.INSTANCE.getScene(handler.getOrderId());
       if (Objects.nonNull(scene)){
           scene.getDriver().addEvent(handler);
       }
    }
}
