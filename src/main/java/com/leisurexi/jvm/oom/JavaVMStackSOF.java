package com.leisurexi.jvm.oom;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @description: 栈深度大于虚拟机所允许的最大深度 StackOverflowError 异常示例
 * VM Args: -Xss128k -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 9:44
 * @since: JDK1.8
 */
@Slf4j
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();
        try {
            javaVMStackSOF.stackLeak();
        } catch (Throwable e) {
            log.info("stack length: {}", javaVMStackSOF.stackLength);
            throw e;
        }
    }

}
