package com.whk.tick;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class TickEvent implements Runnable {

    private long updateTime;

    private TICK_ENUM tickEnum;

    private List<Runnable> runnableList = new LinkedList<>();

    public TickEvent(TICK_ENUM tickEnum) {
        this.tickEnum = tickEnum;
        updateTime = System.currentTimeMillis();
    }

    /**
     * 检查时间
     *
     * @param now 现在时间戳
     * @return 检查条件是否满足
     */
    boolean check(long now, long diff) {
        if (diff > tickEnum.getDiff()) {
            updateTime = now;
            return true;
        }
        return false;
    }

    /**
     * 启动
     */
    void fireEvent() {
        long now = System.currentTimeMillis();
        long diff = now - updateTime;
        if (!check(now, diff)) return;
        runnableList.forEach(r -> {
            try {
                r.run();
            } catch (Exception e) {
                log.error("tick event error:", e);
            }
        });
    }


    @Override
    public void run() {
        fireEvent();
    }
}
