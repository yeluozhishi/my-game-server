package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.IQueueCommand;

public interface IProcessor<T extends IQueueCommand> {


    ThreadType getThreadType();


}
