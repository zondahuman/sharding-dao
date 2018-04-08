package com.abin.lee.sharding.dbtable.api.service;

import com.abin.lee.sharding.dbtable.api.model.Order;

import java.util.List;

/**
 * Created by abin on 2018/2/25 0:11.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.service
 */
public interface OrderService {

    void add(Long userId, Order model);

    void update(Long id, Order model);

    void delete(Long id);

    void deleteByParams(Long id, String orderName);

    Order findById(Long id);

    List<Order> findByParams(Long id, String orderName);

    List<Order> findAll();

    int countByParams(Long id, String orderName);

}
