package com.example.demo.conreoller;

import org.springframework.util.Assert;

public class Tesr {
    public static void main(String[] args) {
        method(null);
}

    public static void method(String param) {
//        try {
//            String ste = null;
//            Assert.isNull(ste, "不能为null");
//        }catch (IllegalArgumentException a){
//            a.getCause();
//        }
        String ste = "q";
        Assert.isNull(ste, "不能为null");
    }

}
