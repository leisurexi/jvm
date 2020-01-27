package com.leisurexi.jvm.classloading;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: leisurexi
 * @date: 2020-01-27 4:04 下午
 * @description: 不同的类加载器对 instanceof 关键字运算结果的影响
 * @since JDK 1.8
 */
@Slf4j
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    if (inputStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        try {
            Object obj = classLoader.loadClass("com.leisurexi.jvm.classloading.ClassLoaderTest").newInstance();
            log.info(String.valueOf(obj.getClass()));
            log.info(String.valueOf(obj instanceof ClassLoaderTest));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
