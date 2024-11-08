package com.whk.threadpool.handler;

import com.whk.threadpool.ThreadType;

/**
 * 执行方法，一次创建即可。
 */
public interface IRecord {

    void doAction(Object... message);

    ThreadType threadType();
}
