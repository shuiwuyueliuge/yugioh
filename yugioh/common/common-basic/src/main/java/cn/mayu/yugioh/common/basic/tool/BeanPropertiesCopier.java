package cn.mayu.yugioh.common.basic.tool;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @description: java bean 属性复制
 * 使用{@link BeanCopier}进行属性拷贝，使用对象池减少{@link BeanCopier}的创建次数。
 * 使用{@link ConstructorAccess}新建对象,对 ConstructorAccess 进行缓存，提高效率。
 * {@link ConstructorAccess}的缓存上限为512，淘汰策略使用lru{@link LinkedHashMap}实现
 * @author: YgoPlayer
 * @time: 2021/5/25 3:42 下午
 */
public class BeanPropertiesCopier {

    private static final int MAX_CACHE_SIZE = 512;

    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new LRU<>(MAX_CACHE_SIZE);

    /**
     * 对象属性拷贝 把source的属性拷贝到target
     *
     * @param source 原对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    public static <T> T copyProperties(T source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }

        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        T target = newInstance(constructorAccess, targetClass);
        copyProperties(source, target);
        return target;
    }

    public static <T> List<T> copyProperties(List<?> sourceList, Class<T> targetClass) {
        if (sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        return sourceList.stream().map(source -> {
            T target = newInstance(constructorAccess, targetClass);
            copyProperties(source, target);
            return target;
        }).collect(Collectors.toList());
    }

    private static <T> T newInstance(ConstructorAccess<T> constructorAccess, Class<T> targetClass) {
        try {
            return constructorAccess.newInstance();
        } catch (RuntimeException e) {
            try {
                return targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e1) {
                throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
            }
        }
    }

    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = CONSTRUCTOR_ACCESS_CACHE.get(targetClass.getName());
        if (constructorAccess != null) {
            return constructorAccess;
        }

        constructorAccess = ConstructorAccess.get(targetClass);
        CONSTRUCTOR_ACCESS_CACHE.put(targetClass.getName(), constructorAccess);
        return constructorAccess;
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClazz, Class<?> targetClazz) {
        String key = generatorKey(sourceClazz, targetClazz);
        if (BEAN_COPIER_MAP.containsKey(key)) {
            return BEAN_COPIER_MAP.get(key);
        }

        BeanCopier copier = BeanCopier.create(sourceClazz, targetClazz, false);
        BEAN_COPIER_MAP.put(key, copier);
        return copier;
    }

    private static String generatorKey(Class<?> sourceClazz, Class<?> targetClazz) {
        return sourceClazz + "_" + targetClazz;
    }

    private static class LRU<K, V> extends LinkedHashMap<K, V> {

        private final int maxCapacity;

        private static final float DEFAULT_LOAD_FACTOR = 0.75f;

        public LRU(int maxCapacity) {
            super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
            this.maxCapacity = maxCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            synchronized (LRU.class) {
                return size() > maxCapacity;
            }
        }

        @Override
        public V get(Object key) {
            synchronized (LRU.class) {
                return super.get(key);
            }
        }

        @Override
        public V put(K key, V value) {
            synchronized (LRU.class) {
                return super.put(key, value);
            }
        }
    }
}
