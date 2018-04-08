package com.abin.lee.sharding.dbtable.api.datasource;

import com.abin.lee.sharding.dbtable.api.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * Created by abin on 2018/2/24 23:56.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.enums
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelectIdentity {
    DataSourceType source() default DataSourceType.master;
}
