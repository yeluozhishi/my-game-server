package com.whk.message.gamegate;

import com.whk.message.ReqMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPlayerListMessage extends ReqMessage {
    private long userId;
    private int serverId;
}
