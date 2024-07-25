package com.whk.listener.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginEvent implements IEvent{
    private long userId;
}
