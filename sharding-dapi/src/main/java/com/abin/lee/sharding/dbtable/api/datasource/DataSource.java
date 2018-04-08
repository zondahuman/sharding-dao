package com.abin.lee.sharding.dbtable.api.datasource;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by abin on 2018/2/26 16:56.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.datasource
 */
public enum DataSource implements Basic {
    orderMaster1("orderMaster1"),
    orderSlave1("orderSlave1"),
    orderMaster2("orderMaster2"),
    orderSlave2("orderSlave2"),
    businessMaster1("businessMaster1"),
    businessSlave1("businessSlave1"),
    businessMaster2("businessMaster2"),
    businessSlave2("businessSlave2");

    private String identity;

    private DataSource(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String identity() {
        return identity;
    }


    public static String getMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            for (DataSource houseDataSource : DataSource.values()) {
                if (houseDataSource.identity.equals(message)) {
                    return houseDataSource.identity();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DataSource dataSource = DataSource.valueOf("MASTER");
        System.out.println(dataSource);
        String name = DataSource.valueOf("MASTER").name().toLowerCase();
        System.out.println(name);

    }
}
