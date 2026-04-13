package com.whk.error;

import com.whk.message.MESSAGE_CODE;

public class FastGameErrorException extends GameErrorException{
    public FastGameErrorException(MESSAGE_CODE code) {
        super(code);
    }
}
