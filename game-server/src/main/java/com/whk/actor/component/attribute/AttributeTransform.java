package com.whk.actor.component.attribute;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.reflect.Field;

public class AttributeTransform {

    public static final float PROP_100 = 100.0f;
    public static final float PROP_10000 = 10000.0f;
    public static final float PROP_1000_0000 = 10000000.0f;
    public static final float PROP_10000_0000 = 100000000.0f;
    public static final double PROP_10000_D = 10000d;
    public static final int PROP_10000_I = 10000;

    public static BiMap<Integer, String> AttributeName;
    public static final BiMap<String, Integer> NameTransformId = HashBiMap.create();

    static {
        Field[] fields = Attribute.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            NameTransformId.put(fields[i].getName(), i);
        }
        AttributeName = NameTransformId.inverse();
    }

}