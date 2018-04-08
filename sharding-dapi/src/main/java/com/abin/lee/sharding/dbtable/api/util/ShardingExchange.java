package com.abin.lee.sharding.dbtable.api.util;

import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by abin on 2018/2/28 0:12.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.util
 */
@Slf4j
@Component
public class ShardingExchange {

    @Autowired
    Environment environment;

    public String shardingTableName(Long functionId) {
        String dbGene = environment.getProperty("sharding.database.available");
        Integer dbGeneAvailable = Ints.tryParse(dbGene);
        String tableGene = environment.getProperty("sharding.table.available");
        Integer tableGeneAvailable = Ints.tryParse(tableGene);
        Long tableId = (functionId / dbGeneAvailable) & (tableGeneAvailable - 1);
        String tableName = "order" + tableId;
        return tableName;
    }

    public String shardingDatabaseName(Long functionId) {
        String gene = environment.getProperty("sharding.database.available");
        Integer sequenceGene = Ints.tryParse(gene);
        Long dbId = functionId & (sequenceGene - 1);
        String dbName = "order" + dbId;
        return dbName;
    }



    public Long selectSharfingTable(Long functionId) {
        String dbGene = environment.getProperty("sharding.database.available");
        Integer dbGeneAvailable = Ints.tryParse(dbGene);
        String tableGene = environment.getProperty("sharding.table.available");
        Integer tableGeneAvailable = Ints.tryParse(tableGene);
        Long tableId = (functionId / dbGeneAvailable) & (tableGeneAvailable - 1);
        return tableId;
    }

    public Integer shardingTableGene() {
        String gene = environment.getProperty("sharding.table.available");
        Integer tableGeneAvailable = Ints.tryParse(gene);
        return tableGeneAvailable;
    }

    public Long selectShardingDatabase(Long functionId) {
        String gene = environment.getProperty("sharding.database.available");
        Integer dbGeneAvailable = Ints.tryParse(gene);
        Long dbId = functionId & (dbGeneAvailable - 1);

        return dbId;
    }

    public Integer shardingDatabaseGene() {
        String gene = environment.getProperty("sharding.database.available");
        Integer dbGeneAvailable = Ints.tryParse(gene);
        return dbGeneAvailable;
    }

}
