package com.whk.aop;

import com.whk.annotation.HandlerDescription;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AspectHandlerDescription {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Before(value = "@annotation(description)")
    public void execute(HandlerDescription description){
        logger.info("method desc:%s, %s".formatted(description.number(), description.desc()));
    }
}
