package com.whk.net.rpc.serialize.wrapper;

import java.util.List;
import java.util.Objects;

public class ListWrapper<T> {
    private List<T> list;

    public ListWrapper(List<T> list){
        this.list = list;
    }

    public List<T> immutableList() {
        if (Objects.isNull(list)) {
            return List.of();
        } else {
            return list;
        }
    }
}
