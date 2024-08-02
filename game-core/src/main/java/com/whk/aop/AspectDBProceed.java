package com.whk.aop;

import com.whk.threadpool.DriverProcessor;
import com.whk.threadpool.HandlerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Aspect
@Component
public class AspectDBProceed {

    @Around(value = "@annotation(around)")
    public Object execute(ProceedingJoinPoint point, AroundAnnotation around) throws ExecutionException, InterruptedException {
        Long orderId = (Long) point.getArgs()[0];
        if (around.hasReturn()){
            FutureTask<Object> futureTask = new FutureTask<>(() -> {
                try {
                    return point.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
            DriverProcessor.INSTANCE.addDbHandler(orderId, HandlerFactory.INSTANCE.creatDBHandler(futureTask));
            return futureTask.get();
        } else {
            DriverProcessor.INSTANCE.addDbHandler(orderId, HandlerFactory.INSTANCE.creatDBHandler(() -> {
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
