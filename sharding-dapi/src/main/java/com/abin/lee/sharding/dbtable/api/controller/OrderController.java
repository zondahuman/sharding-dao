package com.abin.lee.sharding.dbtable.api.controller;

import com.abin.lee.sharding.dbtable.api.model.Order;
import com.abin.lee.sharding.dbtable.api.service.OrderService;
import com.abin.lee.sharding.dbtable.common.generator.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by abin on 2018/2/25 0:59.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.controller
 * http://blog.csdn.net/neosmith/article/details/61202084
 */
@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
//    @Qualifier("orderService")
    OrderService orderService;


    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(Long userId, String orderName, Long businessId){
        String result = "FAILURE";
        try {
            Order model = new Order();
            model.setId(SnowflakeIdWorker.getId(userId));
            model.setVersion(0);
            model.setUpdateTime(new Date());
            model.setCreateTime(new Date());
            model.setOrderName(orderName);
            model.setBusinessId(businessId);
            model.setUserId(userId);
            this.orderService.add(userId, model);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Long id){
        String result = "FAILURE";
        try {
            this.orderService.delete(id);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(Long id, String orderName, Long businessId, Integer version){
        String result = "FAILURE";
        try {
            Order model = new Order();
            model.setVersion(0);
            model.setUpdateTime(new Date());
            model.setOrderName(orderName);
            model.setBusinessId(businessId);
            model.setId(id);
            this.orderService.update(id, model);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/findById")
    @ResponseBody
    public Order findById(Long id){
        Order model = null;
        try {
            model = this.orderService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = "/findByParams")
    @ResponseBody
    public List<Order>  findByParams(Long id, String orderName){
        List<Order> modelList = null;
        try {
            modelList = this.orderService.findByParams(id, orderName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    }


    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Order> findAll(){
        List<Order> modelList = null;
        try {
            modelList = this.orderService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    }


    @RequestMapping(value = "/deleteByParams")
    @ResponseBody
    public String deleteByParams(Long id, String orderName){
        String result = "FAILURE";
        try {
            this.orderService.deleteByParams(id, orderName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/countByParams")
    @ResponseBody
    public Integer countByParams(Long id, String orderName){
        Integer result = 0;
        try {
            result = this.orderService.countByParams(id, orderName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
