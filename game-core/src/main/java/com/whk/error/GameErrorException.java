package com.whk.error;

import lombok.Getter;
import lombok.Setter;
import reactor.util.function.Tuple2;

@Setter
@Getter
public class GameErrorException extends RuntimeException {

    private int code;

    public GameErrorException(Tuple2<Integer, String> tuple2) {
        super(tuple2.getT2());
        this.code = tuple2.getT1();
    }

}
