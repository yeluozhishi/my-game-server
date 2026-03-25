package com.whk.exception;

public class FastGameErrorException extends GameErrorException{
    public FastGameErrorException(int code, String message) {
        super(code, message);
    }
}
