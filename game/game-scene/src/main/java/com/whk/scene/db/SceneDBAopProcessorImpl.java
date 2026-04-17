package com.whk.scene.db;

import com.whk.DBAroundAnnotation;
import com.whk.SpringUtils;
import com.whk.aop.AspectDBProcess;
import com.whk.aop.DBAopProcessor;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.threadpool.processor.ProcessorManager;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SceneDBAopProcessorImpl implements DBAopProcessor {

    public SceneDBAopProcessorImpl() {
        SpringUtils.getBean(AspectDBProcess.class).setDbAopProcessor(this);
    }

    @Override
    public Object process(DBAroundAnnotation around, ProceedingJoinPoint point) throws ExecutionException, InterruptedException {
        FutureTask<Object> futureTask = new FutureTask<>(() -> {
            try {
                return point.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
        ProcessorManager.INSTANCE.process(HandlerFactory.INSTANCE.createDbHandler((Long) point.getArgs()[0], futureTask));
        return futureTask.get();
    }

    @Override
    public void processNoReturn(DBAroundAnnotation around, ProceedingJoinPoint point) {
        ProcessorManager.INSTANCE.process(HandlerFactory.INSTANCE.createDbHandler((Long) point.getArgs()[0], () -> {
            try {
                point.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
