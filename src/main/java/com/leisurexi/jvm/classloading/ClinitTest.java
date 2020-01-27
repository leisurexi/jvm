package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-27 3:21 下午
 * @description:
 * @since JDK 1.8
 */
@Slf4j
public class ClinitTest {

    static class Parent {
        public static int A = 1;

        static {
            A = 2;
            //这边会提示 "Illegal forward references"，因为静态语句块中只能访问到定义在静态语句块之前的变量
            //定义在它之后的变量，在前面的静态语句块中可以赋值，但是不能访问
//            A = C;
        }

        public static int C = 1;
    }

    static class Sub extends Parent {
        public static int B = 2;
    }

    public static void main(String[] args) {
        log.info(String.valueOf(Sub.B));
    }

}
