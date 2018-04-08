package com.abin.lee.sharding.dbtable.api.service.impl;

import com.abin.lee.sharding.dbtable.api.mapper.BusinessMapper;
import com.abin.lee.sharding.dbtable.api.model.Business;
import com.abin.lee.sharding.dbtable.api.model.BusinessExample;
import com.abin.lee.sharding.dbtable.api.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abin on 2018/2/25 0:12.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.service.impl
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessMapper businessMapper;

    @Override
    public void add(Business model) {
        this.businessMapper.insert(model);
    }

    @Override
    public void update(Business model) {
        this.businessMapper.updateByPrimaryKey(model);

    }

    @Override
    public void delete(Long id) {
        this.businessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Business findById(Long id) {
        return this.businessMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Business> findAll() {
        BusinessExample example = new BusinessExample();
        List<Business> businessList = this.businessMapper.selectByExample(example);
        return businessList;
    }



}
