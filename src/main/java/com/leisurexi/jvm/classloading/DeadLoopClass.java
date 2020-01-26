package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-26 9:52 下午
 * @description:
 * @since JDK 1.8
 */
@Slf4j
public class DeadLoopClass {

    static {
        if (true) {
            log.info("{} init DeadLoopClass", Thread.currentThread());
            while (true) {

            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                log.info("{} start", Thread.currentThread());
                DeadLoopClass deadLoopClass = new DeadLoopClass();
                log.info("{} run over", Thread.currentThread());
            }
        };
        Thread thread1 = new Thread(script, "thread-1");
        Thread thread2 = new Thread(script, "thread-2");
        thread1.start();
        thread2.start();
    }

}
