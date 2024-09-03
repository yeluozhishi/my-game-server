package com.whk.threadpool.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SceneHandler extends AbstractHandler {

    private Object message;

    public SceneHandler(IHandler record) {
        super(record);
    }

    public SceneHandler(Object message, IHandler record) {
        super(record);
        this.message = message;
    }

    @Override
    public void run() {
        getRecord().doAction(message);
    }
}
