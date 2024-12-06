package com.whk.threadpool.processor;

public interface IProcessor<Handler> {


    void message(Handler handler);

}
