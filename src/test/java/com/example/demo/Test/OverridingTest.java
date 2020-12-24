package com.example.demo.Test;


import lombok.Data;

class Dog{
    public void bark(){
        System.out.println("woof ");
    }
}

@Data
class Perper{
    private String m;
    private String n;
}
class Hound extends Dog{
    public void sniff(){
        System.out.println("sniff ");
    }

    public void bark(){
        System.out.println("bowl");
    }
}

public class OverridingTest{
    public static void main(String [] args){
//        Dog dog = new Hound();
//        dog.bark();
        Perper p=new Perper();
        p.setM("1");
        p.setN("1");
        capo( p);
        System.out.println(p);
    }

    public static void capo(Perper a){
        a.setN("2");
    }

}