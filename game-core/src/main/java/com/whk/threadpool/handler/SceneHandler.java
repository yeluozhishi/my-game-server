package com.whk.threadpool.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SceneHandler extends AbstractHandler {

    private Object message;

    private String sceneId;

    public SceneHandler(IRecord record) {
        super(record);
    }


    @Override
    public void run() {
        getRecord().doAction(message);
    }

}
