package com.whk.threadpool.processor;

import com.whk.threadpool.handler.IQueueCommand;

public interface IProcessor<T extends IQueueCommand> {


    void message0(T handler);

}
