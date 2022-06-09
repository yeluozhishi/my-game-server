package com.whk.rpc.serialize;

public enum RpcSerializeProtocol {
    JDKSERIALIZE("jdknative"), KRYOSERIALIZE("kryo"), HESSIANSERIALIZE("hessian"), PROTOSTUFFSERIALIZE("protostuff");

    private final String serializeProtocol;

    RpcSerializeProtocol(String serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }


    public String getProtocol() {
        return serializeProtocol;
    }
}
