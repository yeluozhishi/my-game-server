package com.whk.threadpool.processor;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.handler.AbstractHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public abstract class AbstractMessageProcessor<T extends AbstractHandler> implements IProcessor<T> {

    // 驱动器池
    private final Map<String, IDriver> driverMap = new HashMap<>();

    public <T2 extends AbstractHandler> void message(T2 handler) {
        message0((T) handler);
    }

    public void removeDriver(String id) {
        IDriver driver = driverMap.remove(id);
        if (Objects.nonNull(driver)) log.info("移除驱动器:{}-{}", driver.getName(), id);
    }

    public void stop() {
        driverMap.values().forEach(IDriver::stop);
    }
}
