package com.whk.threadpool.processor;

import com.whk.threadpool.handler.AbstractHandler;

public interface IProcessor<T extends AbstractHandler> {


    void message0(T handler);

}
