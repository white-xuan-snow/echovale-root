package com.echovale.shared.redis;




public interface RedisStore<V> {

    void set(V value, Object... args);
    void increment(Object... args);
    V get(Object... args);
    void delete(Object... args);
    boolean hasKey(Object... args);
}
