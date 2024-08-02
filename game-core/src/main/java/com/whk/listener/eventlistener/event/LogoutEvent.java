package com.whk.listener.eventlistener.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutEvent implements IEvent{
    private long userId;
}
