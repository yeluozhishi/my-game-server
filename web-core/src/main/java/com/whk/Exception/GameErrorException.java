package com.whk.Exception;

import com.whk.network_param.IServerError;

public class GameErrorException extends RuntimeException{

    private IServerError error;

    public GameErrorException(IServerError error, String msg){
        super(msg);
        this.error = error;
    }

    public GameErrorException(IServerError error){
        this.error = error;
    }


    public IServerError getError() {
        return error;
    }

    public void setError(IServerError error) {
        this.error = error;
    }
}
