package com.leisurexi.jvm.execute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: leisurexi
 * @date: 2020-02-01 5:13 下午
 * @description: JavaClass 执行工具
 * @since JDK 1.8
 */
public class JavaClassExecute {

    /**
     * 执行外部穿国外的代表一个 Java 类的 Byte 数组
     * 将输入类的 Byte 数组中代表 java.lang.System 的 CONSTANT_Utf8_info 常量修改为
     * 劫持后的 HackSystem 类，执行方法为该类的 static main(String[] args) 方法，输出
     * 结果为该类向 System.out/err 输出的信息
     *
     * @param classByte 代表一个 Java 类的 Byte 数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier classModifier = new ClassModifier(classByte);
        byte[] modifyBytes = classModifier.modifyUTF8Constant("java/lang/System", "com/leisurexi/jvm/execute/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modifyBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }

}
