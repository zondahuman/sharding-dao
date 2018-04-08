package com.abin.lee.sharding.dbtable.api.split;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.abin.lee.sharding.dbtable.api.split.service.Strategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by abin on 2018/4/8 22:04.
 * sharding-dao
 * com.abin.lee.sharding.dbtable.api.split
 */
@Component
public class StrategyManager{
    private  Log log= LogFactory.getLog(StrategyManager.class);
    private  Map<String,Strategy> strategies = new ConcurrentHashMap<String,Strategy>(10);

    @Resource
    Strategy shardingTableStrategy;

    @PostConstruct
    public void init(){
        strategies.put("shardingTableStrategy", shardingTableStrategy);
    }

    public  Strategy getStrategy(String key){
        return strategies.get(key);
    }

    public   Map<String, Strategy> getStrategies() {
        return strategies;
    }

    public  void setStrategies(Map<String, String> strategies) {
        for(Entry<String, String> entry : strategies.entrySet()){
            try {
                this.strategies.put(entry.getKey(),(Strategy)Class.forName(entry.getValue()).newInstance());
            } catch (Exception e) {
                log.error("实例化策略出错", e);
            }
        }
        printDebugInfo();
    }
    private void printDebugInfo(){
        StringBuffer msg= new StringBuffer("初始化了"+strategies.size()+"策略");
        for(String key: strategies.keySet()){
            msg.append("\n");
            msg.append(key);
            msg.append("  --->  ");
            msg.append(strategies.get(key));
        }
        log.debug(msg.toString());
    }
}










