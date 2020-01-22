package com.leisurexi.jvm.gc;

import com.leisurexi.jvm.util.MemoryUtil;

/**
 * @author: leisurexi
 * @description: 新生代 GC 示例
 * @date: 2020/1/22 15:02
 * @since: JDK1.8
 */
public class MinorGC {

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
     * <p>
     * 大多数情况下，对象在新生代 Eden 区分配。当 Eden 区没有足够的空间进行分配时，虚拟机将发起一次 Minor GC。
     * 下面示例中尝试分配3个2MB大小和1个4MB大小的对象，在运行时通过 -Xms20M -Xmx20M -Xmn10M 这3个参数限制
     * Java 堆大小为20MB，且不可扩展，其中10MB分配给新生代，剩下的10MB分配给老年代。-XX:SurvivorRatio=8
     * 决定了新生代中 Eden 区与一个 Survivor 区的空间比例是8比1，从输出的结果也能清晰地看到
     * “ eden space 8192K, from space 1024K, to   space 1024K ” 的信息，新生代总可用空间为
     * 9216KB (Eden 区 + 1个 Survivor 区的总容量)。执行下面代码中分配 allocation4 对象的语句时会发生一次
     * Minor GC，这个 GC 的结果是新生代 6273KB 变为 776KB，而总内存占用量则几乎没有减少 (因为 allocation1、2、3
     * 三个对象都是存活的，虚拟机几乎没有找到可回收的对象)。这次 GC 发生的原因是给 allocation4 分配内存的时候，
     * 发现 Eden 已经被占用了 6MB，剩余空间已不足以分配 allocation4 所需的 4MB 内存，因此发生 Minor GC。
     * GC 期间虚拟机又发现已有的3个2MB大小的对象无法放入 Survivor 空间 (Survivor 空间只有1MB大小)，所以只好
     * 通过分配担保机制提前转移到老年代去。
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * MemoryUtil._1MB];
        allocation2 = new byte[2 * MemoryUtil._1MB];
        allocation3 = new byte[2 * MemoryUtil._1MB];
        //出现一次 Minor GC
        allocation4 = new byte[4 * MemoryUtil._1MB];
    }

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:PretenureSizeThreshold=3142728 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
     * <p>
     * 大对象直接进入老年代，所谓大对象就是指，需要大量连续内存空间的Java对象，最典型的大对象就是那种很长的字符串
     * 及数组 (例如下面代码中的 byte 数组)。大对象对于虚拟机的内存分配来说就是一个坏消息，经常出现大对象容易导致
     * 还有不少空间时就提前触发垃圾收集以获取足够的连续空间来 “安置” 它们。虚拟机提供了一个 -XX:PretenureSizeThreshold
     * 参数，令大于这个设置值的对象直接在老年代中分配。这样做的目的是避免在 Eden 区及两个 Survivor 区之间发生大
     * 量的内存拷贝 (新生待采用复制算法收集内存)。执行以下代码，可以看到 Eden 区空间几乎没有被使用，而老年代10MB
     * 的空间被使用了40%，也就是4MB的 allocation 对象直接就分配在老年代中，这是因为 PretenureSizeThreshold
     * 被设置为3MB (就是3142728，这个参数不能与 -Xms 之类的参数一样直接写3MB)，因此超过3MB的对象都会直接在老年
     * 代中进行分配。
     * </p>
     * PretenureSizeThreshold 参数只对 Serial 和 ParNew 两款收集器有效，Parallel Scavenge 收集器不认识这
     * 个参数，Parallel Scavenge 收集器一般并不需要设置。如果遇到必须使用此参数的场合，可以考虑 ParNew 加 CMS
     * 的收集器组合。
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * MemoryUtil._1MB];
    }

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution -XX:PretenureSizeThreshold=3142728 -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[MemoryUtil._1MB / 4];
        //什么时候进入老年代取决于 XX:MaxTenuringThreshold 设置
        allocation2 = new byte[4 * MemoryUtil._1MB];
        allocation3 = new byte[4 * MemoryUtil._1MB];
        allocation3 = null;
        allocation3 = new byte[4 * MemoryUtil._1MB];
    }

    public static void main(String[] args) {
//        testAllocation();
//        testPretenureSizeThreshold();
        testTenuringThreshold();
    }


}
