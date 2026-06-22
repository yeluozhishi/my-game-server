package com.whk.net.rpc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlayerInfo implements Serializable {
    private Long id;
    private Integer career;
    private Byte sex;
    private Long lastLogin;
    private String name;
    private Integer level;
}
