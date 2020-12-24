package com.example.demo.Test;

import org.junit.Test;

import java.io.InputStream;

public class TestInteger {

    /**
     * 测试包装类型存储长度
     */
    @Test
    public  void testInteger() {
        Integer integer1 = 3;
        Integer integer2 = 3;

        if (integer1 == integer2)
            System.out.println("integer1 == integer2");
        else
            System.out.println("integer1 != integer2");

        Integer integer3 = 128;
        Integer integer4 = 128;

        if (integer3 == integer4)
            System.out.println("integer3 == integer4");
        else
            System.out.println("integer3 != integer4");
    }

    /**
     *
     */
    @Test
    public void testString (){
        String str="str";
//        StringBuffer buffer=new StringBuffer(str);
//        buffer.append("new");
//        System.out.println(buffer.toString());
        System.out.println(str.hashCode());
        String b=str;
        System.out.println(b);
        System.out.println(b.hashCode());
    }

    @Test
    public void testSplicing(){
        long t1 = System.currentTimeMillis();
        StringBuilder str=new StringBuilder();

        //这里是初始字符串定义
        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
             str.append(i);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("cost:" + (t2 - t1));
    }

    @Test
    public void testSplicing2(){
        long t1 = System.currentTimeMillis();
        String str=new String();

        for (int i = 0; i < 50000; i++) {
            //这里是字符串拼接代码
            str=str+i;
        }
        String s=new String(str);
        long t2 = System.currentTimeMillis();
        System.out.println("cost:" + (t2 - t1));
    }

    @Test
    public void testTime(){
        System.out.println(System.currentTimeMillis());
    }
}
