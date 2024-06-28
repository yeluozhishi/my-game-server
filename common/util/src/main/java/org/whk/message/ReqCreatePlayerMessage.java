package org.whk.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreatePlayerMessage extends ReqMessage{
    private long userId;
    private int sex;
    private int kind;
}
