package com.whk.rpc.api.provider;

import com.whk.rpc.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";  
    }  
  
}  
