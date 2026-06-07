package com.whk;


import lombok.Getter;
import org.springframework.data.domain.Example;
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
public abstract class AbstractCacheableService<T extends AbstractEntity<ID>, ID, C extends AbstractCacheableData<T, ID>> implements BaseService<T, ID> {

    protected JpaRepository<T, ID> baseRepository;


    private final Map<ID, C> cache = new ConcurrentHashMap<>();

    private final BatchOperateDB<T, ID> batchOperateDB;

    public AbstractCacheableService() {
        batchOperateDB = new BatchOperateDB<>();
    }

    /**
     * 创建一个空的缓存对象
     * @return 缓存对象
     */
    public abstract C createNullCacheableData();

    /**
     * 设置数据库操作对象
     * @param baseRepository 数据库操作对象
     */
    public abstract void setBaseRepository(JpaRepository<T, ID> baseRepository);

    /**
     * 将数据库对象转为缓存对象
     * @param t 数据库对象
     * @return 缓存对象
     */
    public abstract C transferToObject(T t);

    public C getCache(ID id) {
        C c = cache.get(id);
        if (Objects.nonNull(c)) {
            c.setUpdateTime(System.currentTimeMillis());
            return c;
        }
        return null;
    }

    public void addCache(ID id, C c) {
        c.setUpdateTime(System.currentTimeMillis());
        cache.put(id, c);
    }

    public C removeCache(ID id) {
        return cache.remove(id);
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
            if (Objects.nonNull(c) && !c.isNull()) {
                list.add(c);
            } else idSet.add(id);
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
            if (!cache.containsKey(id)) {
                addCache(id, createNullCacheableData());
            }
        }
        return list;
    }

    @DBAroundAnnotation()
    public C find(ID id) {
        C cachedData = getCache(id);
        if (Objects.nonNull(cachedData)) {
            if (cachedData.isNull()) return null;
            return cachedData;
        }

        Optional<T> optionalEntity = getBaseRepository().findById(id);
        if (optionalEntity.isEmpty()) {
            addCache(id, createNullCacheableData());
            return null;
        }

        T entity = optionalEntity.get();
        C newData = transferToObject(entity);
        newData.setEntity(entity);
        addCache(newData.getId(), newData);
        newData.setUpdateTime(System.currentTimeMillis());

        if (newData.isNull()) return null;
        return newData;
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteByIdImmediately(ID id) {
        removeCache(id);
        getBaseRepository().deleteById(id);
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteById(ID id) {
        C c = removeCache(id);
        if (Objects.nonNull(c)) {
            batchOperateDB.addEntity(this, c.getId(), c.getEntity(), PersistType.DELETE);
        }
    }

    @DBAroundAnnotation()
    public List<C> findByExample(long orderId, Example<T> example) {
        if (example == null) return new LinkedList<>();
        List<T> res = getBaseRepository().findAll(example);
        if (res.isEmpty()) return new LinkedList<>();
        List<C> list = new LinkedList<>();
        res.forEach(t -> {
            C c = transferToObject(t);
            c.setEntity(t);
            addCache(c.getId(), c);
            list.add(c);
        });
        return list;
    }

    @DBAroundAnnotation()
    public C findOneByExample(long orderId, Example<T> example) {
        T t = getBaseRepository().findOne(example).orElse(null);
        if (t == null) return null;
        C c = transferToObject(t);
        c.setEntity(t);
        addCache(c.getId(), c);
        return c;
    }

    @DBAroundAnnotation()
    public C updateImmediately(long orderId, T t) {
        T re = getBaseRepository().saveAndFlush(t);
        C c = transferToObject(re);
        c.setEntity(re);
        c.setUpdateTime(System.currentTimeMillis());
        addCache(c.getId(), c);
        return c;
    }

    @DBAroundAnnotation(hasReturn = false)
    public void update(long orderId, C c) {
        addCache(c.getId(), c);
        batchOperateDB.addEntity(this, c.getId(), c.getEntity(), PersistType.UPDATE);
    }

    @DBAroundAnnotation()
    public boolean exists(ID id) {
        return Objects.nonNull(find(id));
    }

    @DBAroundAnnotation()
    public List<C> findAll(long orderId) {
        return getBaseRepository().findAll().stream().map(this::transferToObject).collect(Collectors.toList());
    }


    public void checkCache() {
        batchOperateDB.batchUpdate();

        if (cache.isEmpty()) return;
        long now = System.currentTimeMillis();
        cache.values().forEach(c -> {
            if (!c.isNull() && (now - c.getUpdateTime() > 300000)) {
                cache.remove(c.getId());
            }
        });
    }
}
