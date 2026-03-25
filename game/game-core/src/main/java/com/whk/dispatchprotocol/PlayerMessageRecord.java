package com.whk.dispatchprotocol;

import com.whk.threadpool.processor.ProcessorId;

import java.lang.reflect.Method;


public record PlayerMessageRecord(Method method, Object clazz, int messageId, ProcessorId processorId) {
}

