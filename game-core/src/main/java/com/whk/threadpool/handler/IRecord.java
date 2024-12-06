package com.whk.threadpool.handler;

/**
 * 执行方法，一次创建即可。
 */
public interface IRecord {

    void doAction(Object... message);

}
