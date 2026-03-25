package com.whk.message.gamegate;

import com.whk.message.ReqMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreatePlayerMessage extends ReqMessage {
    private long userId;
    private int sex;
    private int kind;
}
