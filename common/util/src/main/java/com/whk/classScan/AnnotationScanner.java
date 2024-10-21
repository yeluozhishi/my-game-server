package com.whk.classScan;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

@Getter
@Setter
public class AnnotationScanner implements IClassScan {

    Logger logger = Logger.getLogger(AnnotationScanner.class.getName());

    private final String RESOURCE_PATTERN = "/**/*%s".formatted(CLASS_SUFFIX);


    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(packageName) + RESOURCE_PATTERN;
        Resource[] resources = resourcePatternResolver.getResources(pattern);
        //MetadataReader 的工厂类
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        List<Class<?>> list = new LinkedList<>();
        for (Resource resource : resources) {
            //用于读取类信息
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            //扫描到的class
            String classname = reader.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(classname);
            if (Objects.isNull(predicate) || predicate.test(clazz)) {
                //将注解中的类型值作为key，对应的类作为 value
                list.add(clazz);
            }
        }
        return list;
    }
}
