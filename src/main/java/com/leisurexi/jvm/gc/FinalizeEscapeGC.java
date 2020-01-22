package com.leisurexi.jvm.gc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: leisurexi
 * @description: 此代码演示了两点：
 * 1. 对象可以在被 GC 时自我拯救。
 * 2. 这种自救机会只有一次，因为一个对象的 finalize() 方法最多只会被系统自动调用一次。
 * @date: 2020/1/21 13:44
 * @since: JDK1.8
 */
@Slf4j
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        log.info("yes, i am still alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info("finalize method executed!");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        //因为 Finalizer 方法优先级很低，暂停 0.5 秒，以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            log.info("no, i am dead!");
        }

        //下面这段代码和上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        SAVE_HOOK = null;
        System.gc();
        //因为 Finalizer 方法优先级很低，暂停 0.5 秒，以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            log.info("no, i am dead!");
        }
    }

}
