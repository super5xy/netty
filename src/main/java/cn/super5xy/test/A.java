package cn.super5xy.test;

/**
 * @Author: super5xy
 * @Date: 2020/6/28 19:51
 */
public class A {
     B b;
     int num=1;

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.b=b;
        b.a=a;
        

    }
}
