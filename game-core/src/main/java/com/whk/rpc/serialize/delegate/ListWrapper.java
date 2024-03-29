package com.whk.rpc.serialize.delegate;

import java.util.List;
import java.util.Objects;

public class ListWrapper<T> {
    private List<T> list;

    public ListWrapper(List list){
        this.list = list;
    }

    public List<T> immutableList() {
        if (Objects.isNull(list)) {
            return List.of();
        } else {
            return list;
        }
    }

    public List<T> mutableList() {
        if (Objects.isNull(list)) {
            return List.of();
        } else {
            return list;
        }
    }
}
