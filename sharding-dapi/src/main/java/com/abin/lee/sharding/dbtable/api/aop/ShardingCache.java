package com.abin.lee.sharding.dbtable.api.aop;

import java.lang.annotation.*;

/**
 * Created by abin on 2018/2/28 17:26.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.aop
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShardingCache {
    /**
     * cache key，可以空，默认是class+method
     * @return
     */
    String cacheKey() default "";
    /**
     * Cache过期时间，单位秒，默认5分钟, 0表示永不过期.
     */
    int expiry() default 300;

    /**
     * 是否cachenull
     * @return
     */
    boolean cacheNull() default true;



}
