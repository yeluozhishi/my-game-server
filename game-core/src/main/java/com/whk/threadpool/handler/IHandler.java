package com.whk.threadpool.handler;

import com.whk.threadpool.ThreadType;

public interface IHandler {

    void doAction(Object... message);

    ThreadType threadType();
}
