package org.whk.classScan;

import java.util.List;
import java.util.function.Predicate;

public interface IClassScan {

    String CLASS_SUFFIX = ".class";

    List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException;

    default List<Class<?>> search(String packageName) throws ScannerClassException {
        return search(packageName, this.getClass().getClassLoader(), null);
    }
}
