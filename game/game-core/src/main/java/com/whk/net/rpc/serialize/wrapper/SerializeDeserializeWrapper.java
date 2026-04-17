package com.whk.net.rpc.serialize.wrapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SerializeDeserializeWrapper<T> {
    //泛型的使用
    private T data;

    //建造者模式(返回实体类型)
    public static <T> SerializeDeserializeWrapper<T> builder(T data) {
        SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<T>();
        wrapper.setData(data);
        return wrapper;
    }

}