package com.leisurexi.jvm.oom;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: leisurexi
 * @description: 运行时常量池异常导致的内存溢出异常
 * JDK 1.6下，会出现“PermGen Space”的内存溢出，而在 JDK 1.7和 JDK 1.8 中，会出现堆内存溢出，并
 * 且 JDK 1.8中 PermSize 和 MaxPermGen 已经无效。因此，可以大致验证 JDK 1.7 和 1.8 将字符串常量由永久代转移到堆中，
 * 注意，此时仅仅是字符串常量池转移到了堆中，但是运行时常量池依旧还是在方法区里；并且 JDK 1.8 中已经不存在永久代的结论
 * VM Args: -Xms6M -Xmx6M -XX:-UseGCOverheadLimit -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 * @date: 2020/1/21 10:19
 * @since: JDK1.8
 */
@Slf4j
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        //使用 List 保持着常量池引用，避免 Full GC 回收常量池行为
//        List<String> list = new ArrayList<>();
//        int i = 0;
//        while (true) {
//            list.add(String.valueOf(i++).intern());
//        }

        /**
         * 下面代码在JDK 6中运行，会得到两个false，而在JDK 7及后续版本中运行，会得到一个true和一个false。
         * 产生差异的原因是，在JDK 6中，intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中
         * 存储，返回的也是这个字符串实例的引用，而由StringBuilder创建的字符串对象实例在Java堆上，所以必然
         * 不可能是同一个引用，结果将返回false。
         * 而JDK 7的intern()方法实现就不需要拷贝字符串的实例到永久代了，既然字符串常量池已经移到Java堆中，
         * 那只需要在常量池里记录一下首次出现的实例引用即可，因此intern()方法返回的引用和由StringBuilder
         * 创建的那个字符串实例就是同一个。而对str2比较返回false，这是因为"java"(它是在加载sum.misc.Version
         * 这个类的时候进入字符串常量池的)这个字符串在执行StringBuilder.toString()之前就已经出现过了，
         * 字符串常量池中已经有它的引用，不符合intern()方法要求"首次遇到"的原则，"计算机软件"这个字符串则
         * 是首次出现的，因此结果返回true。
         */
        String str1 = new StringBuilder("计算机").append("软件").toString();
        log.info(String.valueOf(str1.intern() == str1));
        String str2 = new StringBuilder("ja").append("va").toString();
        log.info(String.valueOf(str2.intern() == str2));
    }

}
