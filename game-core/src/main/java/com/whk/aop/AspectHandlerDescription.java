package com.whk.aop;

import com.whk.annotation.HandlerDescription;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectHandlerDescription {
    @Before(value = "@annotation(description)")
    public void execute(HandlerDescription description){
        log.info("method desc:%d, %s".formatted(description.number(), description.desc()));
    }
}
