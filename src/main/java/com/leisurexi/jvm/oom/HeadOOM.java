package com.leisurexi.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: leisurexi
 * @description: 堆内存溢出示例
 * VM Args: -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 9:17
 * @since: JDK1.8
 */
public class HeadOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
