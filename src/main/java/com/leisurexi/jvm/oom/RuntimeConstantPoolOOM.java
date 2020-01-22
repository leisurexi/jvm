package com.leisurexi.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: leisurexi
 * @description: 运行时常量池异常导致的内存溢出异常
 * JDK 1.6下，会出现“PermGen Space”的内存溢出，而在 JDK 1.7和 JDK 1.8 中，会出现堆内存溢出，并
 * 且 JDK 1.8中 PermSize 和 MaxPermGen 已经无效。因此，可以大致验证 JDK 1.7 和 1.8 将字符串常量由永久代转移到堆中，
 * 注意，此时仅仅是字符串常量池转移到了堆中，但是运行时常量池依旧还是在方法区里；并且 JDK 1.8 中已经不存在永久代的结论
 * VM Args: -Xms20M -Xmx20M -XX:-UseGCOverheadLimit -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 10:19
 * @since: JDK1.8
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        //使用 List 保持着常量池引用，避免 Full GC 回收常量池行为
        List<String> list = new ArrayList<>();
        // 10MB 的 PermSize 在 integer 范围内足够产生 OOM 了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

}
