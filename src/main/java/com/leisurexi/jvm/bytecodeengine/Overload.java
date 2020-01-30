package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author: leisurexi
 * @date: 2020-01-28 12:39 下午
 * @description: 重载方法匹配优先级
 * @since JDK 1.8
 */
@Slf4j
public class Overload {

//    public static void sayHello(Object arg) {
//        log.info("hello object");
//    }

//    public static void sayHello(int arg) {
//        log.info("hello int");
//    }

//    public static void sayHello(long arg) {
//        log.info("hello long");
//    }

//    public static void sayHello(Character arg) {
//        log.info("hello Character");
//    }

//    public static void sayHello(char arg) {
//        log.info("hello char");
//    }

    public static void sayHello(char... arg) {
        log.info("hello char...");
    }

//    public static void sayHello(Serializable arg) {
//        log.info("hello Serializable");
//    }

    /**
     * 下面代码会输出 hello char，这很好理解，'a'是一个char类型的数据，自然会寻找参数类型为char
     * 的重载方法。如果注释掉 sayHello(char arg) 方法，那么输出会变为：hello int，这时发生了
     * 一次自动类型转换，'a'除了可以代表一个字符串外，还可以代表数字65(字符'a'的Unicode数值为十
     * 进制数字65)，因此参数类型为int的重载也是合适的。我们继续注释掉 sayHello(int arg)方法，
     * 那么输出会变为：hello long，这时发生了两次自动类型转换，'a'转换为整数65之后，进一步转换为
     * 长整数65L，匹配了参数类型为long的重载。代码中没有编写其他类型(如float、double等)的重载，
     * 不过实际上自动转型还能继续发生多次，按照 char->int->long->float->double 的顺序转型进
     * 行匹配。但不会匹配到byte和short类型的重载，因为char到byte或short的转型是不安全的。我们
     * 继续注释掉 sayHello(long arg) 方法，那么输出会变为：hello Character，这时发生了一次
     * 自动装箱，'a'被包装为它的封装类型 java.lang.Character，所以匹配到了参数类型为Character
     * 的重载。继续注释掉 sayHello(Character arg)方法，那么输出会变为：hello Serializable，
     * 这个输出可能会让人感觉摸不着头南，一个字符或数字与序列化有什么关系？出现hello Serializable，
     * 是因为java.lang.Serializable是java.lang.Character类实现的一个接口，自动装箱之后发现还
     * 是找不到装箱类，但是找到了装箱类实现了的接口类型，所以紧接着又发生一次自动转型。char可以转型成
     * int，但是Character是绝对不会转型为Integer的，它只能安全地转型为它实现的接口或父类。Character
     * 还实现了另外一个接口java.lang.Comparable<Character>，如果同时出现两个参数分别为Serializable
     * 和Comparable<Character>的重载方法，那么它们此时的优先级是一样的。编译器无法确定要自动转型为
     * 哪种类型，会提示类型模糊，拒绝编译。程序必须在调用时显示地指定字面量的静态类型，如
     * sayHello((Comparable<Character>'a'))，才能通过编译。下面继续注释掉 sayHello(Serializable arg)
     * 方法，输出会变为：hello Object，这时是char装箱后转型为父类了，如果有多个父类，那么将在继承关
     * 系中从下往上开始搜索，越接近上次的优先级越低。即使方法调用传入的参数值为null，这个规则仍然使用。
     * 我们把 sayHello(Object arg) 也注释掉，输出将会变为：hello char...，七个重载方法已经被注释
     * 得只剩一个了，可见边长参数的重载优先级是最低的，这时候字符'a'被当作了一个数组元素。这里使用的是
     * char类型的变长参数，还可以在验证时选择int类型、Character类型、Object类型等的变长参数重载来
     * 把上面的过程重新演示一遍。但是要注意的是，有一些在单个参数中能成立的自动转型，如char转型为int，
     * 在变长参数中是不成立的。
     */
    public static void main(String[] args) {
        sayHello('a');
    }

}
