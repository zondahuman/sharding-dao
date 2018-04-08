package com.abin.lee.sharding.dbtable.api.split.service.impl;

import com.abin.lee.sharding.dbtable.api.split.service.Strategy;
import com.abin.lee.sharding.dbtable.api.util.ShardingExchange;
import com.abin.lee.sharding.dbtable.common.util.JsonUtil;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by abin on 2018/4/8 21:54.
 * sharding-dao
 * com.abin.lee.sharding.dbtable.api.split.service.impl
 */
@Slf4j
@Component
public class ShardingTableStrategy implements Strategy {

    @Autowired
    ShardingExchange shardingExchange;

    @Override
    public String convert(Map<String, Object> params) {
        log.info("params= " + JsonUtil.toJson(params));
        Long id = null ;
        if(null != params.get("execute_param_values")){
            Map<String, Object> paramsObject = (Map<String, Object>)params.get("execute_param_values") ;
            id = Longs.tryParse(paramsObject.get("id").toString()) ;
        }else{
            log.info("params= " + params);
        }

        String destinationName = this.shardingExchange.shardingTableName(id);

        return destinationName;
    }

}