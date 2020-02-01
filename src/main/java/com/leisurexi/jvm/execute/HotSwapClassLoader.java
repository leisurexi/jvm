package com.leisurexi.jvm.execute;

/**
 * @author: leisurexi
 * @date: 2020-02-01 4:39 下午
 * @description: 为了多次载入执行类而加入的加载器，把 defineClass 方法开放出来，只有外部显式
 * 调用的时候才会使用到 loadByte 方法，由虚拟机调用时，仍然按照原有的双亲委派规则使用 loadClass
 * 方法进行类加载
 * @since JDK 1.8
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}
