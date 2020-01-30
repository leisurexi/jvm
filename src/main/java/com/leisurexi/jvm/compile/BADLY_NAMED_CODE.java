package com.leisurexi.jvm.compile;

/**
 * @author: leisurexi
 * @date: 2020-01-28 9:46 下午
 * @description: 包含了多处不规范命名的代码样例
 * @since JDK 1.8
 */
public class BADLY_NAMED_CODE {

    enum colors {
        red, blue, green;
    }

    static final int _FORTY_TWO = 42;

    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BADLY_NAMED_METHOD() {
        return;
    }

    public void NOTcamelCASEmethodNAME() {
        return;
    }

}
