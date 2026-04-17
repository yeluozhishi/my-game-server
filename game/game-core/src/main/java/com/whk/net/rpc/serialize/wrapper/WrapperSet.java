package com.whk.net.rpc.serialize.wrapper;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WrapperSet {
    private final Set<Class<?>> wrapperSet = new HashSet<>();
    private static WrapperSet INSTANCE = new WrapperSet();

    private WrapperSet() {
        wrapperSet.add(List.class);
        wrapperSet.add(ArrayList.class);
        wrapperSet.add(CopyOnWriteArrayList.class);
        wrapperSet.add(LinkedList.class);
        wrapperSet.add(Stack.class);
        wrapperSet.add(Vector.class);
        wrapperSet.add(Map.class);
        wrapperSet.add(HashMap.class);
        wrapperSet.add(TreeMap.class);
        wrapperSet.add(LinkedHashMap.class);
        wrapperSet.add(Hashtable.class);
        wrapperSet.add(SortedMap.class);
    }

    public static WrapperSet getInstance() {
        if (INSTANCE == null) {
            synchronized (WrapperSet.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WrapperSet();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isWrapper(Class<?> clazz) {
        return wrapperSet.contains(clazz);
    }
}
