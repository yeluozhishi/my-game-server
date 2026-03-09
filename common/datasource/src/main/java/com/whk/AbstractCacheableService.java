package com.whk;


import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 缓存服务
 * IEntity会保存AbstractCacheableData序列化数据，需要将序列化数据转为AbstractCacheableData
 * AbstractCacheableData需要序列化保存到IEntity
 * 定时批量执行增删改：缓存对象引用和操作。
 *
 * @param <T>
 * @param <ID>
 * @param <C>
 */
@Getter
public abstract class AbstractCacheableService<T extends IEntity, ID, C extends AbstractCacheableData<T, ID>> implements BaseService<T, ID>{

    protected JpaRepository<T, ID> baseRepository;


    private final Map<ID, C> cache = new ConcurrentHashMap<>();

    private final BatchOperateDB<T, ID> batchOperateDB;

    public AbstractCacheableService() {
        batchOperateDB = new BatchOperateDB<>();
    }


    public abstract void setBaseRepository(JpaRepository<T, ID> baseRepository);

    public abstract C transferToObject(T t);

    public C getCache(ID id) {
        C c = cache.get(id);
        if (Objects.nonNull(c)) {
            if (c.isNull()) return null;
            c.setUpdateTime(System.currentTimeMillis());
            return c;
        }
        return null;
    }

    public void addCache(ID id, C c) {
        c.setUpdateTime(System.currentTimeMillis());
        cache.put(id, c);
    }

    @DBAroundAnnotation()
    public List<C> findAllByIds(long orderId, Iterable<ID> ids) {
        if (ids == null) {
            return new LinkedList<>();
        }
        List<C> list = new LinkedList<>();
        Set<ID> idSet = new HashSet<>();
        for (ID id : ids) {
            C c = getCache(id);
            if (Objects.nonNull(c)) {
                list.add(c);
            }
            else idSet.add(id);
        }
        if (idSet.isEmpty()) return list;
        List<T> ts = getBaseRepository().findAllById(idSet);
        for (T t : ts) {
            C c = transferToObject(t);
            c.setUpdateTime(System.currentTimeMillis());
            c.setEntity(t);
            addCache(c.getId(), c);
            list.add(c);
            idSet.remove(c.getId());
        }
        for (ID id : idSet) {
            if (!cache.containsKey(id)){
                cache.put(id, (C) NullCacheableData.INSTANCE);
            }
        }
        return list;
    }

    @DBAroundAnnotation()
    public C find(ID id) {
        C c = getCache(id);
        if (Objects.nonNull(c)) {
            return c;
        }
        Optional<T> t = getBaseRepository().findById(id);
        if (t.isEmpty()) {
            // todo whk
            cache.put(id, (C) NullCacheableData.INSTANCE);
            return c;
        }
        c = transferToObject(t.get());
        c.setEntity(t.get());
        cache.put(c.getId(), c);
        c.setUpdateTime(System.currentTimeMillis());
        if (c.isNull()) return null;
        return c;
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteByIdImmediately(ID id) {
        cache.remove(id);
        getBaseRepository().deleteById(id);
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteById(ID id) {
        C c = cache.remove(id);
        batchOperateDB.addEntity(this, c.getId(), c.getEntity(), PersistType.DELETE);
    }


    @DBAroundAnnotation(hasReturn = false)
    public void create(ID id, C c) {
        addCache(c.getId(), c);
        getBaseRepository().saveAndFlush(c.getEntity());
    }

    @DBAroundAnnotation(hasReturn = false)
    public void updateImmediately(long orderId, C c) {
        addCache(c.getId(), c);
        getBaseRepository().saveAndFlush(c.getEntity());
    }

    @DBAroundAnnotation(hasReturn = false)
    public void update(long orderId, C c) {
        addCache(c.getId(), c);
        batchOperateDB.addEntity(this, c.getId(), c.getEntity(), PersistType.UPDATE);
    }

    @DBAroundAnnotation()
    public boolean exists(ID id) {
        C c = find(id);
        return Objects.nonNull(c) && !c.isNull();
    }

    @DBAroundAnnotation()
    public List<C> findAll(long orderId) {
        return getBaseRepository().findAll().stream().map(this::transferToObject).collect(Collectors.toList());
    }


    public void checkCache() {
        batchOperateDB.batchUpdate();

        if (cache.isEmpty()) return;
        cache.values().forEach(c -> {
            if (!c.isNull() && System.currentTimeMillis() - c.getUpdateTime() > 300000) {
                cache.remove(c.getId());
            }
        });
    }
}
