package com.whk.Exception;

public class FastGameErrorException extends GameErrorException{
    public FastGameErrorException(int code, String message) {
        super(code, message);
    }
}
