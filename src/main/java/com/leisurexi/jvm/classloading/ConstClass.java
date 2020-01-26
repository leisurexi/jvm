package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-26 9:13 上午
 * @description: 被动使用类字段演示三：常量在编译节点会存入调用类的常量池中，本质上
 * 没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。
 * @since JDK 1.8
 */
@Slf4j
public class ConstClass {

    static {
        log.info("ConstClass init!");
    }

    public static final String HELLO_WORLD = "hello world";

}
