package com.whk.aop;

import com.whk.annotation.HandlerDescription;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectHandlerDescription {
    @Around(value = "@annotation(description)")
    public Object execute(ProceedingJoinPoint point, HandlerDescription description) throws Throwable {
        log.info("method : {}, desc: {}, player: {}, msg: {}", point.getSignature().getName(), description.desc(), point.getArgs()[1], point.getArgs()[0]);
        return point.proceed();
    }
}
