package com.abin.lee.sharding.dbtable.common.test;

import org.junit.Test;

/**
 * Created by abin on 2018/2/28 20:56.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.common.test
 */
public class LowFourBit {
    public static void main(String[] args) {
        System.out.println(Integer.toOctalString(11));
        System.out.println(Integer.toBinaryString(11));
        System.out.println(Integer.toHexString(11));

        long result = 0010 | 4 << 4;
        System.out.println("result-="+result);
    }

    @Test
    public void hex(){
        Integer param = 12 ;
        System.out.println(Integer.toHexString(param));
    }
}
