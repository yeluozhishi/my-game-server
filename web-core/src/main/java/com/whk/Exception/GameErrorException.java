package com.whk.Exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameErrorException extends RuntimeException {

    private int code;

    public GameErrorException(int code, String message) {
        super(message);
        this.code = code;
    }

}
