package com.whk.Exception;

import org.slf4j.helpers.MessageFormatter;

public class GameErrorException extends RuntimeException {

    private int code;

    public GameErrorException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static void main(String[] args) {
        String msg = MessageFormatter.arrayFormat("你好,{}", new String[] {"cccc"}).getMessage();
        System.out.println(msg);
    }

}
