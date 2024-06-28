package org.whk.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class ReqMessage implements Serializable {
    private String toke;
}
