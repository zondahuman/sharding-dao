package com.abin.lee.sharding.dbtable.api.datasource;

//import com.abin.lee.sharding.dbtable.api.interceptor.TpsDeleteInterceptor;
//import com.abin.lee.sharding.dbtable.api.interceptor.TpsInsertInterceptor;
//import com.abin.lee.sharding.dbtable.api.interceptor.TpsUpdateInterceptor;
import com.abin.lee.sharding.dbtable.api.split.TableSplitInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis基础配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.abin.lee.sharding.dbtable.api.mapper")
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Autowired
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.abin.lee.sharding.dbtable.api.model");
//        bean.setPlugins(new Interceptor[]{sqlStatsInterceptor(),tpsDeleteInterceptor(), tpsInsertInterceptor(), tpsUpdateInterceptor()});
//        bean.setPlugins(new Interceptor[]{sqlStatsInterceptor(), tableSplitInterceptor()});
        bean.setPlugins(new Interceptor[]{tableSplitInterceptor()});
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean
    public SQLStatsInterceptor sqlStatsInterceptor(){
        SQLStatsInterceptor sqlStatsInterceptor = new SQLStatsInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        sqlStatsInterceptor.setProperties(properties);
        return sqlStatsInterceptor;
    }

    @Bean
    public TableSplitInterceptor tableSplitInterceptor(){
        TableSplitInterceptor tableSplitInterceptor = new TableSplitInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        tableSplitInterceptor.setProperties(properties);
        return tableSplitInterceptor;
    }

//    @Bean
//    public TpsDeleteInterceptor tpsDeleteInterceptor(){
//        TpsDeleteInterceptor tpsDeleteInterceptor = new TpsDeleteInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("dialect", "mysql");
//        tpsDeleteInterceptor.setProperties(properties);
//        return tpsDeleteInterceptor;
//    }
//
//    @Bean
//    public TpsInsertInterceptor tpsInsertInterceptor(){
//        TpsInsertInterceptor tpsInsertInterceptor = new TpsInsertInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("dialect", "mysql");
//        tpsInsertInterceptor.setProperties(properties);
//        return tpsInsertInterceptor;
//    }
//
//    @Bean
//    public TpsUpdateInterceptor tpsUpdateInterceptor(){
//        TpsUpdateInterceptor tpsUpdateInterceptor = new TpsUpdateInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("dialect", "mysql");
//        tpsUpdateInterceptor.setProperties(properties);
//        return tpsUpdateInterceptor;
//    }


}
