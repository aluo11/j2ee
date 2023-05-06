package com.j2ee.utils;
import com.alibaba.druid.sql.visitor.functions.Char;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public class RandomId {
    /**
     * 生成十位随机数字
     */
    public static String getRanmdomId() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(RandomUtils.nextInt(0,10)); //每次随机出一个数字（0-9）
        }
        return str.toString();
    }

    /**
     * 随机座位号
     * @return
     */
    public static String getRandomSiteNum(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //生成一个随机字母
        int number=random.nextInt(26);
        sb.append(str.charAt(number));
        //生成一个随机数字
        //选定随机数的生成区间为7~15  1-10
        //15-7=8  10-1=9
        //8+1=9 
        int i = random.nextInt(10) + 1;
        sb.append(i);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomSiteNum());
    }
}
