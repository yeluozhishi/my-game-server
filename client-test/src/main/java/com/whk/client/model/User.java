package com.whk.client.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {
    private String userName = "whk";
    private String token;
    private String pwd = "123";

    private Long playerId = 1111L;
    private Long userId = 1111L;

    private Integer serverId;


}
