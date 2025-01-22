package com.whk.aop;

import com.whk.annotation.DBAroundAnnotation;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Aspect
@Component
public class AspectDBProcess {

    @Around(value = "@annotation(around)")
    public Object execute(ProceedingJoinPoint point, DBAroundAnnotation around) throws ExecutionException, InterruptedException {
        Long orderId = (Long) point.getArgs()[0];
        if (around.hasReturn()) {
            FutureTask<Object> futureTask = new FutureTask<>(() -> {
                try {
                    return point.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
            ProcessorManager.INSTANCE.process(ProcessorId.DB_PROCESSOR, HandlerFactory.INSTANCE.createDbHandler(orderId, futureTask));
            return futureTask.get();
        } else {
            ProcessorManager.INSTANCE.process(ProcessorId.DB_PROCESSOR, HandlerFactory.INSTANCE.createDbHandler(orderId, () -> {
                try {
                    point.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        return null;
    }
}
