package com.leisurexi.jvm.bytecodeengine;

/**
 * @author: leisurexi
 * @date: 2020-01-28 10:15 上午
 * @description: 局部变量表 Slot 复用对垃圾收集的影响
 * @since JDK 1.8
 */
public class SlotTest {

    public static void main(String[] args) {
        {
            byte[] bytes = new byte[64 * 1024 * 1024];
        }
        /**
         * 不加下面这行代码bytes数组不被回收的根本原因是：局部变量表中的Slot是否还存有
         * 关于bytes数组对象的引用。上面代码中bytes的作用域虽然已经被限制在大括号内，
         * 但在此之后，没有任何局部变量表的读写操作，bytes原本所占用的Slot还没有被其他
         * 变量所复用，所以作为GC Roots一部分的局部变量表仍然保持着对它的关联。这种关联
         * 没有被及时打断，在绝大部分情况下影响都很轻微。但如果遇到一个方法，其后面的代码
         * 有一些耗时很长的操作，而前面又定义了占用了大量内存、实际上已经不会再被使用的变
         * 量，手动将其设置为null值 (用来代替下面的 int a = 0，把变量对应的局部变量表
         * Slot清空) 就不是一个毫无意义的操作，这种操作可以作为一种在极特殊情形 (对象占
         * 用内存大、此方法的栈帧长时间不能被回收、方法调用次数打不到 JIT 的编译条件) 下
         * 的"奇技"来使用。
         */
        int a = 0;
        System.gc();
    }

}
