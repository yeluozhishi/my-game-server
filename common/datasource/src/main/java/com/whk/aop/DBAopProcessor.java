package com.whk.aop;

import com.whk.DBAroundAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.ExecutionException;

public interface DBAopProcessor {
    Object process(DBAroundAnnotation around, ProceedingJoinPoint point) throws ExecutionException, InterruptedException;


    void processNoReturn(DBAroundAnnotation around, ProceedingJoinPoint point);
}
