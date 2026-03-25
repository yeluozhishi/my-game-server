package com.whk.aop;

import com.whk.DBAroundAnnotation;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Aspect
@Component
@Setter
public class AspectDBProcess {

    private DBAopProcessor dbAopProcessor;

    public AspectDBProcess() {
        dbAopProcessor = new DefaultDBAopProcessor();
    }

    @Around(value = "@annotation(around)")
    public Object execute(ProceedingJoinPoint point, DBAroundAnnotation around) throws ExecutionException, InterruptedException {
        if (around.hasReturn()) {
            return dbAopProcessor.process(around, point);
        } else {
            dbAopProcessor.processNoReturn(around, point);
        }
        return null;
    }
}
