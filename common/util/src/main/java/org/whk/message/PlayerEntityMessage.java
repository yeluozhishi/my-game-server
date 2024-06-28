package org.whk.message;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerEntityMessage extends ResMessage{
    private long id;

    private Long userAccountId;

    private Integer career;

    private Byte sex;

    private Long lastLogin;
}