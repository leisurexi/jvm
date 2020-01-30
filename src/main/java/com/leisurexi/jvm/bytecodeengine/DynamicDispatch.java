package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-28 1:05 下午
 * @description: 方法动态分派演示
 * @since JDK 1.8
 */
@Slf4j
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            log.info("man say hello");
        }
    }

    static class Women extends Human {
        @Override
        protected void sayHello() {
            log.info("women say hello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        man.sayHello();
        women.sayHello();
        man = new Women();
        man.sayHello();
    }

}
