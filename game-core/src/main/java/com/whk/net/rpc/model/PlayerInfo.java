package com.whk.net.rpc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class PlayerInfo implements Serializable {
    private Long id;
    private Integer career;
    private Byte sex;
    private Long lastLogin;
}
