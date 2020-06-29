package cn.super5xy.netty;

/**
 * @Author: super5xy
 * @Date: 2020/6/23 9:15
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("helloworld");
        Integer i = new Integer(0);
        get(i);
        System.out.println(i);
    }
    private static void get(Integer i){
        i=new Integer(1);
    }
}
