package com.abin.lee.sharding.dbtable.common.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

/**
 * Created by abin on 2018/2/25 0:33.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.common.test
 */
public class AppTest {
    private static final long sequenceMask = -1L ^ (-1L << 4);
    private static final Integer sequenceGene = 4;

    public static void main(String[] args) {
        //十进制转换为二进制
        System.out.println(Integer.toBinaryString(1234));
        String byteString = Integer.toBinaryString(1234);
        String gene = getByte(byteString);
        System.out.println("gene="+gene);


    }

    public static String getByte(String param){
        if(null == param)
            return StringUtils.EMPTY;
        Integer geneLengh = param.length();
        if (geneLengh == sequenceGene)
            return param;
        String geneEnd = "";
        if(geneLengh < sequenceGene){
            for (int i = 0; i <sequenceGene-geneLengh ; i++) {
                geneEnd += "0";
            }
            geneEnd = geneEnd + param;
        }

        if(geneLengh > sequenceGene){
            geneEnd = param.substring(geneLengh - sequenceGene, geneLengh);
        }
        return geneEnd;
    }


}
