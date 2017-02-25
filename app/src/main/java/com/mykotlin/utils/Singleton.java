package com.mykotlin.utils;

/**
 * 单例子模式
 * Created by qiqi on 17/2/25.
 */

public class Singleton {

/**
 * 第一种
 * 懒人模式
 * 类已加载就初始化对象
 * <p>
 * 第二 种
 * 懒加载 模式(不是线程安全的  多个线程来调用会有多个实例  synchronized来加锁 保证线程安全)
 * 在用的时候才去创建对象
 * <p>
 * 第三种
 * /
 * <p>
 * }

 //    private static Singleton instanc = new Singleton();
 //
 //    private Singleton() {
 //    }
 //
 //    public static Singleton getInstance() {
 //        return instanc;
 //
 //    }

 /**
 * 第二 种
 * 懒加载 模式(不是线程安全的  多个线程来调用会有多个实例  synchronized来加锁 保证线程安全)
 * 在用的时候才去创建对象
 */
//    private static Singleton instanc;
//    private Singleton() {
//    }
//
//    public static synchronized Singleton getInstance() {
//        if(instanc==null){
//            instanc=new Singleton();
//        }
//        return instanc;
//
//    }

    /**
     * 第三种
     * volatile 保证实例化 和 赋值是有序的
     */
//    private static volatile Singleton instanc;
//
//    private Singleton() {
//    }
//
//    public static Singleton getInstance() {
//        if (instanc == null) {
//            synchronized (Singleton.class) {
//                if (instanc == null) {
//                    //初始化分为 实例化和赋值 两部
//                    //可以保证 instance为null 就没有初始化完成
//                    //instance不为null 一定初始化完成了
//                    instanc = new Singleton();
//                }
//            }
//        }
//        return instanc;
//    }

    /**
     * 第四种
     * 静态内部类
     */

    private static class Holder {
        private static Singleton instance = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        //此时调用 才类加载holder 类加载是线程安全的
        return Holder.instance;
    }
}
