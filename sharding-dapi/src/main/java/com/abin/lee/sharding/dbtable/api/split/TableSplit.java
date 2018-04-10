package com.abin.lee.sharding.dbtable.api.split;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by abin on 2018/4/8 21:50.
 * sharding-dao
 * com.abin.lee.sharding.dbtable.api.split
 * 首先你要知道在哪些sql上面要处理分表？你可能需要一个注解：
 * git@github.com:dushangkui/common-util.git
 * https://github.com/zondahuman/common-util
 * https://github.com/dushangkui/common-util
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TableSplit {
    //是否分表
    public boolean split() default true;

    //表名
    public String value();

    public String field() default "";

    //获取分表策略
    public String strategy();

}
