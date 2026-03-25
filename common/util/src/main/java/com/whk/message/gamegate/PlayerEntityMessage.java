package com.whk.message.gamegate;

import com.whk.message.ResMessage;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerEntityMessage extends ResMessage {
    private long id;

    private long userAccountId;

    private Integer career;

    private Byte sex;

    private long lastLogin;
}