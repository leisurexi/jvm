package com.leisurexi.jvm.oom;

/**
 * @author: leisurexi
 * @description: 创建线程导致内存溢出异常
 * VM Args: -Xss2M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 10:00
 * @since: JDK1.8
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> dontStop());
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
        javaVMStackOOM.stackLeakByThread();
    }

}
