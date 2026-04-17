package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.handler.IQueueCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
@Setter
@Slf4j
public abstract class AbstractMessageProcessor<T extends IQueueCommand> implements IProcessor<T> {

    // 驱动器池
    private final Map<Long, IDriver> driverMap = new HashMap<>();

    protected abstract IDriver addDriver(long id, ThreadPoolExecutor executor);

    public void message(T handler) {
        IDriver driver = getDriverMap().get(handler.getOrderId());
        if (Objects.isNull(driver)) {
            driver = addDriver(handler.getOrderId(), ThreadPoolManager.getInstance().getExecutor(getThreadType()));
        }
        driver.addEvent(handler);
    }

    public void removeDriver(String id) {
        IDriver driver = driverMap.remove(id);
        if (Objects.nonNull(driver)) log.info("移除驱动器:{}-{}", driver.getName(), id);
    }

    public void stop() {
        driverMap.values().forEach(IDriver::stop);
    }
}
