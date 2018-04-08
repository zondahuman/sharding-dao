package com.abin.lee.sharding.dbtable.api.enums;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by abin on 2018/2/26 17:44.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.enums
 */
public enum LocationSwitchEnum {

    add,
    insert,
    save,

    delete,
    remove,

    update,
    modify,

    find,
    query
    ;

    public static Boolean localtionId(String methodName){
        boolean flag = Boolean.FALSE;
        for (String param:addList()){
            if(methodName.startsWith(param)){
                flag = Boolean.TRUE;
                break;
            }
        }
        return flag;
    }

    public static List<String> addList(){
        List<String> list = Lists.newArrayList();
        list.add(LocationSwitchEnum.add.name());
        list.add(LocationSwitchEnum.insert.name());
        list.add(LocationSwitchEnum.save.name());
        return list;
    }

    public static List<String> otherList(){
        List<String> list = Lists.newArrayList();
        list.add(LocationSwitchEnum.delete.name());
        list.add(LocationSwitchEnum.remove.name());

        list.add(LocationSwitchEnum.update.name());
        list.add(LocationSwitchEnum.modify.name());

        list.add(LocationSwitchEnum.find.name());
        list.add(LocationSwitchEnum.query.name());
        return list;
    }


}
