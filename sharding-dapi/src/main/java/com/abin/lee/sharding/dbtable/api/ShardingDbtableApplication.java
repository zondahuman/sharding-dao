package com.abin.lee.sharding.dbtable.api;

import com.abin.lee.sharding.dbtable.api.datasource.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by abin on 2018/2/24 21:49.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api
 * 首先要将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源。
 * 在@SpringBootApplication注解中添加exclude属性即可：
 */

@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
public class ShardingDbtableApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingDbtableApplication.class, args);
    }


}