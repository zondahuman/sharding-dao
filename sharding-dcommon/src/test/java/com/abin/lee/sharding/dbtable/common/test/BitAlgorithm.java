package com.abin.lee.sharding.dbtable.common.test;

import org.junit.Test;

/**
 * Created by abin on 2018/3/1 18:25.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.common.test
 */
public class BitAlgorithm {
    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    @Test
    public void test1(){
        //-1的正确表示应该是1111 1111，即0xFF
        System.out.println("maxWorkerId= "+maxWorkerId);
        System.out.println("maxWorkerId= " + (-1L << workerIdBits));

    }















}
