package com.whk;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 批量操作数据库
 * 分类，每个类 -> id -> 数据
 */
public class BatchOperateDB<T, ID> {
    private final Map<BaseService<T, ID>, Map<ID, BatchEntity<T>>> entityMap = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock writeLock = lock.writeLock();

    public void addEntity(BaseService<T, ID> baseService, ID id, T entity, PersistType persistType) {
        if (Objects.isNull(entity)) return;
        try {
            writeLock.lock();

            Map<ID, BatchEntity<T>> map = entityMap.getOrDefault(baseService, new HashMap<>());
            if (!entityMap.containsKey(baseService)) {
                entityMap.put(baseService, map);
            }
            if (map.containsKey(id)) {
                BatchEntity<T> batchEntity = map.get(id);
                batchEntity.setPersistType(persistType);
            } else {
                map.put(id, new BatchEntity<>(entity, persistType));
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void batchUpdate() {
        if (entityMap.isEmpty()) return;
        // 转移数据
        Map<BaseService<T, ID>, Map<ID, BatchEntity<T>>> map = new HashMap<>();

        try {
            writeLock.lock();
            if (!entityMap.isEmpty()) {
                map.putAll(entityMap);
                entityMap.clear();
            }
        } finally {
            writeLock.unlock();
        }

        // 批量处理
        map.forEach((baseService, idBatchEntityMap) -> {
            List<T> updateEntities = new LinkedList<>();
            List<T> deleteEntities = new LinkedList<>();
            idBatchEntityMap.values().forEach(f -> {
                switch (f.getPersistType()) {
                    case INSERT:
                    case UPDATE:
                        updateEntities.add(f.getEntity());
                        break;
                    case DELETE:
                        deleteEntities.add(f.getEntity());
                        break;
                }
            });
            if (!updateEntities.isEmpty()) {
                baseService.getBaseRepository().saveAllAndFlush(updateEntities);
            }
            if (!deleteEntities.isEmpty()) {
                baseService.getBaseRepository().deleteAllInBatch(deleteEntities);
            }
        });
    }


    @Getter
    @Setter
    static class BatchEntity<T> {
        private T entity;

        private PersistType persistType;

        public BatchEntity(T entity, PersistType persistType) {
            this.entity = entity;
            this.persistType = persistType;
        }
    }

}
