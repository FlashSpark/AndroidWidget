package com.mt.android.finance.miuitargetwidget.tool;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 排队线程调度类
 * author:ps
 * date:2017/12/6
 */

public class MtTimer {

    public static final int THREAD_POOL_MAX = 1;

    private ScheduledExecutorService threadPool;

    private Future future;

    /**
     * 构造函数
     */
    public MtTimer() {
        threadPool = Executors.newScheduledThreadPool(THREAD_POOL_MAX);
    }

    /**
     * 延时计时器
     *
     * @param task  延时任务
     * @param delay 延时时长 （固定为 ms）
     * @return 任务线程对象
     */
    public void schedule(Runnable task, long delay) {
        future = threadPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 周期执行定时器
     *
     * @param task         周期任务
     * @param initialDelay 初始延时
     * @param period       执行周期
     * @return 任务线程对象
     */
    public void scheduleAtFixRate(Runnable task, long initialDelay, long period) {
        future = threadPool.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 终止所有定时任务
     */
    public void stop() {
        if (future != null && !future.isDone()) {
            // interrupt and stop timer task
            future.cancel(true);
        }
    }
}
