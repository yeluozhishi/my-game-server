package com.whk.eventlistener.event;

import com.whk.listener.eventlistener.IEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutEvent implements IEvent {
    private long userId;

    @Override
    public long getOrderId() {
        return userId;
    }
}
