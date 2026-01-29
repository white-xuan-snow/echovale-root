package com.echovale.shared.infrastructure.redis;




public interface RedisStore<V> {

    void set(V value, Object... args);
    void increment(Object... args);
    V get(Object... args);
    void delete(Object... args);
    boolean hasKey(Object... args);
    Long getRemainingTtl(Object... args);
}
