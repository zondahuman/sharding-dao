package com.abin.lee.sharding.dbtable.api.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by abin on 2018/3/30 0:12.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.util
 */
@Slf4j
public class TpsStatics {

    public static AtomicLong statics = null;

    static {
        statics = new AtomicLong(0);
    }

    public static void increase(){
        Long result = statics.getAndIncrement();
        log.info("-------------statics-----------------------------=  " + result);
    }


}
