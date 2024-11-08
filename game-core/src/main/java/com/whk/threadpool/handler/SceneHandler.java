package com.whk.threadpool.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SceneHandler extends AbstractHandler {

    private Object message;

    public SceneHandler(IRecord record) {
        super(record);
    }

    public SceneHandler(Object message, IRecord record) {
        super(record);
        this.message = message;
    }

    @Override
    public void run() {
        getRecord().doAction(message);
    }
}
