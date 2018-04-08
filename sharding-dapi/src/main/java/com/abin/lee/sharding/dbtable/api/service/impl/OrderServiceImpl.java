package com.abin.lee.sharding.dbtable.api.service.impl;

import com.abin.lee.sharding.dbtable.api.aop.ShardingCache;
import com.abin.lee.sharding.dbtable.api.datasource.SelectIdentity;
import com.abin.lee.sharding.dbtable.api.enums.DataSourceType;
import com.abin.lee.sharding.dbtable.api.mapper.OrderMapper;
import com.abin.lee.sharding.dbtable.api.model.Order;
import com.abin.lee.sharding.dbtable.api.model.OrderExample;
import com.abin.lee.sharding.dbtable.api.service.OrderService;
import com.abin.lee.sharding.dbtable.api.util.ShardingExchange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by abin on 2018/2/25 0:12.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.service.impl
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ShardingExchange shardingExchange;

    @Override
    @SelectIdentity(source = DataSourceType.master)
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void add(Long id, Order model) {
        this.orderMapper.insert(id, model);
//        if(true)
//            throw new RuntimeException("throw an own define exception ! ");
    }

    @Override
    @SelectIdentity(source = DataSourceType.master)
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void update(Long id, Order model) {
        Order order = this.orderMapper.selectByPrimaryKey(id);
        if(ObjectUtils.notEqual(null, order)){
            order.setBusinessId(model.getBusinessId());
            order.setId(model.getId());
            order.setOrderName(model.getOrderName());
            order.setUpdateTime(model.getUpdateTime());
            order.setVersion(model.getVersion());
        }
        this.orderMapper.updateByPrimaryKey(order);

    }

    @Override
    @SelectIdentity(source = DataSourceType.master)
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(Long id) {
        this.orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    @SelectIdentity(source = DataSourceType.master)
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void deleteByParams(Long id, String orderName) {
//        String tableName = this.shardingExchange.shardingTableName(id);
//        log.info("TABLENAME-------------= " + tableName);
        OrderExample example = new OrderExample();
        String condition = "%" + orderName + "%" ;
        example.createCriteria().andOrderNameLike(condition);
        this.orderMapper.deleteByExample(example);

    }

    @Override
    @SelectIdentity(source = DataSourceType.slave)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public Order findById(Long id) {
        return this.orderMapper.selectByPrimaryKey(id);
    }


    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @param id
     * @param orderName
     * @return
     */
    @Override
//    @ShardingCache(expiry=7200)//缓存2小时
    @SelectIdentity(source = DataSourceType.slave)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public List<Order> findByParams(Long id, String orderName) {
        OrderExample example = new OrderExample();
        String condition = "%" + orderName + "%" ;
        example.createCriteria().andOrderNameLike(condition);
        List<Order> list = this.orderMapper.selectByExample(example);
        return list;
    }

    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @return
     */
    @Override
    @SelectIdentity(source = DataSourceType.slave)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public List<Order> findAll() {
        OrderExample example = new OrderExample();
        List<Order> businessList = this.orderMapper.selectByExample(example);
        return businessList;
    }

    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @param id
     * @param orderName
     * @return
     */
    @Override
    @SelectIdentity(source = DataSourceType.slave)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public int countByParams(Long id, String orderName) {
        OrderExample example = new OrderExample();
        String condition = "%" + orderName + "%" ;
        example.createCriteria().andOrderNameLike(condition);
        Integer total = this.orderMapper.countByExample(example);
        return total;
    }




}
