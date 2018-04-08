package com.abin.lee.sharding.dbtable.api.mapper;

import com.abin.lee.sharding.dbtable.api.model.Order;
import com.abin.lee.sharding.dbtable.api.model.OrderExample;
import java.util.List;

import com.abin.lee.sharding.dbtable.api.split.TableSplit;
import org.apache.ibatis.annotations.Param;

/**
 * https://blog.csdn.net/jiaotuwoaini/article/details/52373883
 */

@TableSplit(split=true, value="order", strategy="shardingTableStrategy", field = "id" )
public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(@Param("id") Long id, @Param("record") Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}