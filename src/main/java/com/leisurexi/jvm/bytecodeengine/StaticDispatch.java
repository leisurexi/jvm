package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-28 12:19 下午
 * @description: 方法静态分派演示
 * @since JDK 1.8
 */
@Slf4j
public class StaticDispatch {

    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Women extends Human {
    }

    public void sayHello(Human guy) {
        log.info("hello, guy!");
    }

    public void sayHello(Man guy) {
        log.info("hello, gentleman!");
    }

    public void sayHello(Women guy) {
        log.info("hello, lady!");
    }

    /**
     * Human man = new Man();
     * 上面代码中的 Human 称为变量的静态类型 (Static Type) 或者外观类型 (Apparent Type)，
     * 后面的 Man 则称为变量的实际类型 (Actual Type)，静态类型和实际类型在程序中都可以发生一
     * 些变化，区别是静态类型的变化仅仅在使用时发生，变量本身的静态类型不会被改变，并且最终的静
     * 态类型的变化仅仅在使用时发生，变量本身的静态类型不会被改变，并且最终的静态类型是在编译期
     * 可知的；而实际类型变化的结果在运行期才可确定，编译器在编译程序的时候并不知道一个对象的实际
     * 类型是什么。
     * main() 里面的两次 sayHello() 方法调用，在方法接受者已经确定是对象 staticDispatch 的
     * 前提下，使用哪个重载版本，就完全取决于传入参数的数量和数据类型。代码中刻意地定了两个静态类型
     * 相同、实际类型不同的变量，但虚拟机 (准确地说是编译器) 在重载时是用过参数的静态类型而不是实际
     * 类型作为判定依据的。并且静态类型是编译期可知的，所以在编译阶段，Javac 编译器就根据参数的静态
     * 类型决定使用哪个重载版本，所以选择了 sayHello(Human) 作为调用目标，并把这个方法的符号引用
     * 写到 main() 方法里的两条 invokevirtual 指令的参数中。
     */
    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(women);
    }

}
