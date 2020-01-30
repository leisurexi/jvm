package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @date: 2020-01-28 1:19 下午
 * @description: 单分派、多分派演示
 * @since JDK 1.8
 */
@Slf4j
public class Dispatch {

    static class QQ {
    }

    static class _360 {
    }

    public static class Father {
        public void hardChoice(QQ arg) {
            log.info("father choose qq");
        }

        public void hardChoice(_360 arg) {
            log.info("father choose 360");
        }
    }

    public static class Son extends Father {
        @Override
        public void hardChoice(QQ arg) {
            log.info("son choose qq");
        }

        @Override
        public void hardChoice(_360 arg) {
            log.info("son choose 360");
        }
    }

    /**
     * 在main函数中调用了两次hardChoice()方法，这两次hardChoice()方法的选择结果在程序
     * 输出中已经显示得很清楚了。
     * 再来看看编译阶段编译器的选择过程，即静态分派的过程。这时候选择目标方法的依据有两点：
     * 一是静态类型是Father还是Son，二是方法参数是QQ还是360。这次选择结果的最终产物是产生
     * 了两条invokevirtual指令，两条指令的参数分别为常量池中指向Father.hardChoice(360)
     * 及Father.hardChoice(QQ)方法的符号引用。因为是根据两个宗量进行选择，所以Java语言的
     * 静态分派属于多分派类型。
     * 再看看运行阶段虚拟机的选择，即动态分派的过程。在执行son.hardChoice(new QQ())这句代
     * 码时，更准确地说，在执行这句代码所对应的invokevirtual指令时，由于编译器已经决定目标方
     * 法的签名必须为hardChoice(QQ)，虚拟机此时不会关心传递过来的参数QQ到底是"腾讯QQ"还是
     * "奇瑞QQ"，因为这时候参数的静态类型、实际类型都不会对方法的选择构成任何影响，唯一可以影
     * 响虚拟机选择的因素只有此方法的接受者的实际类型是Father还是Son。因为只有一个宗量作为选择
     * 依据，所以Java语言的动态分派属于但分派类型。
     */
    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }

}
