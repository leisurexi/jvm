package com.leisurexi.jvm.gc;

import com.leisurexi.jvm.util.MemoryUtil;

/**
 * @author: leisurexi
 * @description: jvm垃圾回收跟搜索算法回收示例，objA 和 objB 循环应用，但还是可以观察 GC 日志发现，对象被回收了
 * @date: 2020/1/21 11:16
 * @since: JDK1.8
 */
public class ReferenceCountingGC {

    public Object instance = null;

    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在 GC 日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * MemoryUtil._1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();
    }

}
