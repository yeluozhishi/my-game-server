package com.whk.actor.attribute;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

record FieldAccessor(Field field, MethodHandle getter, MethodHandle setter) {
}