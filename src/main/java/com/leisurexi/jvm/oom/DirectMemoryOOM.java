package com.leisurexi.jvm.oom;

import com.leisurexi.jvm.util.MemoryUtil;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: leisurexi
 * @description: 本机直接内存溢出示例
 * VM Args: -Xms20M -Xmx20M -XX:MaxDirectMemorySize=10M -XX:-UseGCOverheadLimit -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 11:03
 * @since: JDK1.8
 */
public class DirectMemoryOOM {

    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            unsafe.allocateMemory(MemoryUtil._1MB);
        }
    }

}
