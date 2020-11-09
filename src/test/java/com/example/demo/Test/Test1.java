package com.example.demo.Test;

import java.util.regex.Pattern;

public class Test1 {

    public static void main(String[] args) {
        String strE = "kjdsksklf我拒绝的积极性积分 55ds55dgs5gxdbv56dgs《";
        //String pattern = "[\\u2E80-\\uFE4F]+";
        //String pattern = "[\\u3000-\\u303F]+";
//        String pattern = "[\\u4e00-\\u9fa5]+";
        String pattern = "((?![\\u3000-\\u303F])[\\u2E80-\\uFE4F]|\\·)*(?![\\u3000-\\u303F])[\\u2E80-\\uFE4F](\\·)*$";
        String[] splitStr = strE.split("");

        for(String str:splitStr) {
            if(Pattern.matches(pattern, str))
                System.out.println(str);

        }
        String str="333,222";
        System.out.println("str="+str.substring(4));
    }
}
