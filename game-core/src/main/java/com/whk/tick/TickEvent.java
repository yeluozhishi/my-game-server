package com.whk.tick;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TickEvent {

    private long updateTime;

    private TICK_ENUM tickEnum;

    private List<Runnable> runnableList = new LinkedList<>();

    public TickEvent(TICK_ENUM tickEnum) {
        this.tickEnum = tickEnum;
        updateTime = System.currentTimeMillis();
    }

    public TickEvent(long updateTime, TICK_ENUM tickEnum) {
        this.updateTime = updateTime;
        this.tickEnum = tickEnum;
    }

    /**
     * 检查时间
     * @param now 现在时间戳
     * @return 检查条件是否满足
     */
    boolean check(long now, long diff) {
        if (diff > tickEnum.getDiff()){
            updateTime = now;
            return true;
        }
        return false;
    }

    /**
     * 启动
     */
    void fireEvent(long now) {
        long diff = now - updateTime;
        if (!check(now, diff)) return;
        try {
            runnableList.forEach(Runnable::run);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
