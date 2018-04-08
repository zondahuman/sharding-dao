package com.abin.lee.sharding.dbtable.api.interceptor;

import com.abin.lee.sharding.dbtable.api.util.TpsStatics;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.util.Properties;

/**
 * add sql 设置 createTime updateTime operator update sql 设置 updateTime operator
 * Created by bml on 2015/6/9.
 */

@Intercepts({ @org.apache.ibatis.plugin.Signature(type = Executor.class, method = "insert", args = {
        MappedStatement.class, Object.class }) })
public class TpsInsertInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object obj = invocation.getArgs()[1];
        if (SqlCommandType.UPDATE == mappedStatement.getSqlCommandType()) {
            TpsStatics.increase();
        } else if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType()) {
            TpsStatics.increase();
        } else if (SqlCommandType.DELETE == mappedStatement.getSqlCommandType()) {
            TpsStatics.increase();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}


