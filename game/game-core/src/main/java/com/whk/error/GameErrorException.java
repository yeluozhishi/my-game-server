package com.whk.error;

import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameErrorException extends RuntimeException {

    private int code;

    public GameErrorException(MESSAGE_CODE code) {
        super(MessageI18n.getMessage(code));
        this.code = code.getCode();
    }

}
