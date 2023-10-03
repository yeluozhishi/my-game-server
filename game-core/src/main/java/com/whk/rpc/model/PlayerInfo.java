package com.whk.rpc.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlayerInfo {
    private Long id;
    private Long userAccountId;
    private Integer career;
    private Byte sex;
    private Long lastLogin;
}
