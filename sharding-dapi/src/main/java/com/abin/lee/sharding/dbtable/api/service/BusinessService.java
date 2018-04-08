package com.abin.lee.sharding.dbtable.api.service;

import com.abin.lee.sharding.dbtable.api.model.Business;

import java.util.List;

/**
 * Created by abin on 2018/2/25 0:11.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.service
 */
public interface BusinessService {

    void add(Business model);

    void update(Business model);

    void delete(Long id);

    Business findById(Long id);

    List<Business> findAll();


}
