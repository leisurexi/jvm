package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-28 11:22 上午
 * @description: 方法静态解析演示
 * @since JDK 1.8
 */
@Slf4j
public class StaticResolution {

    public static void sayHello() {
        log.info("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }

}
