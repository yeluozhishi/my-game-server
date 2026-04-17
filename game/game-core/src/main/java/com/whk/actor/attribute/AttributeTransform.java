package com.whk.actor.attribute;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AttributeTransform {

    public static final float PROP_100 = 100.0f;
    public static final float PROP_10000 = 10000.0f;
    public static final float PROP_1000_0000 = 10000000.0f;
    public static final float PROP_10000_0000 = 100000000.0f;
    public static final double PROP_10000_D = 10000d;
    public static final int PROP_10000_I = 10000;


    private AttributeTransform() {
        initializeFieldCache();
    }

    public static AttributeTransform getInstance() {
        return AttributeHelper.attributeTransform;
    }


    private static class AttributeHelper {
        private static final AttributeTransform attributeTransform = new AttributeTransform();
    }

    private final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private final Map<String, FieldAccessor> FIELD_CACHE = new ConcurrentHashMap<>();

    private void initializeFieldCache() {
        Field[] fields = Attribute.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                MethodHandle getter = LOOKUP.unreflectGetter(field);
                MethodHandle setter = LOOKUP.unreflectSetter(field);
                FIELD_CACHE.put(field.getName(), new FieldAccessor(field, getter, setter));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to initialize field accessor for: " + field.getName(), e);
            }
        }
    }

    public FieldAccessor getFieldAccessor(String fieldName) {
        return FIELD_CACHE.get(fieldName);
    }
}