package com.whk.aop;

import com.whk.DBAroundAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;

public class DefaultDBAopProcessor implements DBAopProcessor {
    @Override
    public Object process(DBAroundAnnotation around, ProceedingJoinPoint point) {
        try {
            return point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processNoReturn(DBAroundAnnotation around, ProceedingJoinPoint point) {
        try {
            point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
