package com.whk.error;

import reactor.util.function.Tuple2;

public class FastGameErrorException extends GameErrorException{
    public FastGameErrorException(Tuple2<Integer, String> tuple2) {
        super(tuple2);
    }
}
