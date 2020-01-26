package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: leisurexi
 * @date: 2020-01-26 8:52 上午
 * @description:
 * @since JDK 1.8
 */
@Slf4j
public class NotInitialization {

    /**
     * 非主动使用类字段演示:
     * 只会输出 "Super Class init!"，而不会输出 "SubClass init!"。对于静态字段，只有
     * 直接定义这个字段的类才会被初始化，因此用过其子类来引用父类中定义的静态字段，只会触发
     * 父类的初始化而不会触发子类的初始化。至于是否要触发子类的加载和验证，在虚拟机规范中并
     * 未明确规定，这点取决于虚拟机的具体实现。对于 Sun HotSpot 虚拟机来说，可通过
     * -XX:+TraceClassLoading 参数看到此操作会导致子类的加载。
     */
    @Test
    public void test1() {
        log.info(String.valueOf(SubClass.value));
    }

    /**
     * 被动使用类字段演示二：通过数组定义来引用类，不会触发此类的初始化
     * 运行之后发现没有输出 "SuperClass init!"，说明并没有触发类 com.leisurexi.jvm.clazz.SuperClass
     * 的初始化阶段。但是这段代码里面出发了另外一个名为 [Lcom.leisurexi.jvm.clazz.SuperClass
     * 的类初始化节点，对于用户代码来说，这并不是一个合法的类名称，它是由虚拟机自动生成的、直接
     * 继承于 java.lang.Object 的子类，创建动作由字节码指令 newarray 触发。
     */
    @Test
    public void test2() {
        SuperClass[] sca = new SuperClass[10];

    }

    /**
     * 下面代码运行后，也没有输出 "ConstClass init!"，这是因为虽然在Java源码中引用了 ConstClass
     * 类中的常量 HELLO_WORLD，但是在编译阶段将此常量的值 "hello world" 存储到了NotInitialization
     * 类的常量池中，对常量 ConstClass.HELLO_WORLD 的引用实际都被转化为 NotInitialization 类对自身
     * 常量池的引用了。也就是说实际上 NotInitialization 的 Class 文件之中并没有 ConstClass 类的符号
     * 引用入口，这两个类在编译成 Class 之后就不存在任何联系了。
     */
    @Test
    public void test3() {
        log.info(ConstClass.HELLO_WORLD);
    }

}
