package com.abin.lee.sharding.dbtable.api.split;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by abin on 2018/4/8 21:52.
 * sharding-dao
 * com.abin.lee.sharding.dbtable.api.split
 */
@Component
public class ContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        ContextHelper.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        return ContextHelper.context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
}
