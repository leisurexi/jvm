package com.leisurexi.jvm.compile;

/**
 * @author: leisurexi
 * @date: 2020-01-29 11:05 上午
 * @description: 即时编译结果测试
 * VM Args: -XX:+PrintCompilation -XX:+PrintInlining
 * @since JDK 1.8
 */
public class JITCompileTest {

    public static final int NUM = 15000;

    public static int doubleValue(int i) {
        return i * 2;
    }

    public static long calcSum() {
        long sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            calcSum();
        }
    }

}
