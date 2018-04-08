package com.abin.lee.sharding.dbtable.api.controller;

import com.abin.lee.sharding.dbtable.api.model.Business;
import com.abin.lee.sharding.dbtable.api.service.BusinessService;
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
@RequestMapping("/businesss")
@Slf4j
public class BusinessController {

    @Autowired
//    @Qualifier(value="businessService")
    BusinessService businessService;



    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(String businessName){
        String result = "FAILURE";
        try {
            Business model = new Business();
            model.setVersion(0);
            model.setUpdateTime(new Date());
            model.setCreateTime(new Date());
            model.setBusinessName(businessName);
            this.businessService.add(model);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/delete")
    public String delete(Long id){
        String result = "FAILURE";
        try {
            this.businessService.delete(id);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @RequestMapping(value = "/update")
    public String update(Long id, String businessName){
        String result = "FAILURE";
        try {
            Business model = new Business();
            model.setVersion(0);
            model.setUpdateTime(new Date());
            model.setCreateTime(new Date());
            model.setBusinessName(businessName);
            model.setId(id);
            this.businessService.update(model);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "/findById")
    public Business findById(Long id){
        Business model = null;
        try {
            model = this.businessService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = "/findAll")
    public List<Business> findAll(){
        List<Business> modelList = null;
        try {
            modelList = this.businessService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    }





}
