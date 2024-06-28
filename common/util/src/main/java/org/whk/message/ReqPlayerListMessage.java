package org.whk.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPlayerListMessage extends ReqMessage{
    private long userId;
}
