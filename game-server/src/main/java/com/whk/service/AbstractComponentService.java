package com.whk.service;

import com.whk.SpringUtils;
import com.whk.actor.component.AbstractComponent;
import com.whk.annotation.DBAroundAnnotation;
import com.whk.net.kafka.MessageInnerCoder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractComponentService<T, C extends AbstractComponent<T>> extends AbstractBaseService<T, Long>{

    private final Map<Long, C> cache = new ConcurrentHashMap<>();

    @DBAroundAnnotation()
    public List<C> findAllByIdsComponent(long orderId, Iterable<Long> ids) {
        if (ids == null) {
            return new LinkedList<>();
        }
        List<C> list = new LinkedList<>();
        Set<Long> idSet = new HashSet<>();
        for (Long id : ids) {
            if (cache.containsKey(id)) list.add(cache.get(id));
            else idSet.add(id);
        }
        List<T> ts = getBaseRepository().findAllById(idSet);
        for (T t : ts) {
            C c = transferToObject0(t);
            list.add(c);
            cache.put(c.getId(), c);
        }
        return list;
    }

    @DBAroundAnnotation()
    public C findComponent(long id) {
        if (cache.containsKey(id)) return cache.get(id);
        Optional<T> t = getBaseRepository().findById(id);
        if (t.isEmpty()) return null;
        C c = transferToObject0(t.get());
        cache.put(c.getId(), c);
        return c;
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteByIdComponent(long id) {
        cache.remove(id);
        getBaseRepository().deleteById(id);
    }

    @DBAroundAnnotation()
    public void removeCache(long id) {
        cache.remove(id);
    }

    @DBAroundAnnotation()
    public void createComponent(long id, C c) {
        cache.put(id, c);
        getBaseRepository().saveAndFlush(c.getEntity());
    }

    @DBAroundAnnotation(hasReturn = false)
    public void updateComponent(long orderId, C c) {
        c.setUpdateTime(System.currentTimeMillis());
        getBaseRepository().save(c.getEntity());
    }

    @DBAroundAnnotation()
    public boolean existsComponent(long id) {
        if (cache.containsKey(id)) return true;
        return getBaseRepository().existsById(id);
    }

    public abstract byte[] transferToObject(T t);

    public abstract Class<C> getComponentClass();

    public C transferToObject0(T t) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(transferToObject(t), getComponentClass());
    }

    public void checkCache() {
        if (cache.isEmpty()) return;
        cache.values().forEach(c -> {
            if (System.currentTimeMillis() - c.getUpdateTime() > 300000) {
                cache.remove(c.getId());
            }
        });
    }
}
