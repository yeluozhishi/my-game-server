package com.whk.error;

import reactor.util.function.Tuple2;

public class GameErrorException extends RuntimeException {

    private int code;

    public GameErrorException(Tuple2<Integer, String> tuple2) {
        super(tuple2.getT2());
        this.code = tuple2.getT1();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
