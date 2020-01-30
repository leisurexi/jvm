package com.leisurexi.jvm.bytecodeengine;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: leisurexi
 * @date: 2020-01-28 7:23 下午
 * @description:
 * @since JDK 1.8
 */
@Slf4j
public class DynamicProxyTest {

    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            log.info("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originalObj;

        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("welcome");
            return method.invoke(originalObj, args);
        }
    }

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();

//        Integer a = 1;
//        Integer b = 2;
//        Integer c = 3;
//        Integer d = 3;
//        Integer e = 321;
//        Integer f = 321;
//        Long g = 3L;
//        log.info(String.valueOf(c == d)); //true
//        log.info(String.valueOf(e == f)); //false
//        log.info(String.valueOf(c == (a + b))); //true
//        log.info(String.valueOf(c.equals(a + b))); //true
//        log.info(String.valueOf(g == (a + b))); //true
//        log.info(String.valueOf(g.equals(a + b))); //false
    }

}
