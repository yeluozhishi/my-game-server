package com.whk.listener.eventlistener.event;

import com.whk.threadpool.TheadType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginEvent implements IEvent{
    private long userId;

    @Override
    public long getOrderId() {
        return userId;
    }

    @Override
    public TheadType getTheadType() {
        return TheadType.PLAYER_THREAD;
    }
}
