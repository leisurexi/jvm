package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-26 8:49 上午
 * @description: 被动使用类字段演示一：通过子类引用父类的静态字段，不会导致子类初始化
 * @since JDK 1.8
 */
@Slf4j
public class SuperClass {

    static {
        log.info("Super Class init!");
    }

    public static int value = 123;

}
