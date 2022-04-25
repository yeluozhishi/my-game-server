package com.whk.rpc.consumer.server;

import java.io.Serializable;

public record Server(int id, String ip, int port) implements Serializable {


    public String getAddress() {
        return "/" + ip + ":" + port;
    }
}
