package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-26 8:51 上午
 * @description:
 * @since JDK 1.8
 */
@Slf4j
public class SubClass extends SuperClass {

    static {
        log.info("SubClass init!");
    }

}
