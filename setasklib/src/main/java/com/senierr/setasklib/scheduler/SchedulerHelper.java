package com.senierr.setasklib.scheduler;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程调度
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public class SchedulerHelper {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static SchedulerHelper schedulerHelper;

    // UI主线程
    private Handler mainScheduler;
    // 普通异步线程
    private Executor threadScheduler;
    // 计划线程
    private ScheduledExecutorService scheduledScheduler;

    private SchedulerHelper() {
        mainScheduler = new Handler(Looper.getMainLooper());
        threadScheduler
                = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128));
        scheduledScheduler = Executors.newScheduledThreadPool(MAXIMUM_POOL_SIZE);
    }

    /**
     * 获取线程调度实例
     *
     * @return
     */
    public static SchedulerHelper getInstance() {
        if (schedulerHelper == null) {
            synchronized (SchedulerHelper.class) {
                if (schedulerHelper == null) {
                    schedulerHelper = new SchedulerHelper();
                }
            }
        }
        return schedulerHelper;
    }

    /**
     * UI线程执行任务
     *
     * @param runnable
     */
    public void doOnMainScheduler(Runnable runnable) {
        mainScheduler.post(runnable);
    }

    /**
     * 普通异步线程执行任务
     *
     * @param runnable
     */
    public void doOnThreadScheduler(Runnable runnable) {
        threadScheduler.execute(runnable);
    }

    /**
     * 延迟线程执行任务
     *
     * @param runnable
     * @param delay
     * @param timeUnit
     */
    public void doOnScheduledScheduler(Runnable runnable, long delay, TimeUnit timeUnit) {
        scheduledScheduler.schedule(runnable, delay, timeUnit);
    }

    /**
     * 周期线程执行任务
     *
     * @param runnable
     * @param delay
     * @param period
     * @param timeUnit
     */
    public void doOnScheduledScheduler(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
        scheduledScheduler.scheduleAtFixedRate(runnable, delay, period, timeUnit);
    }
}
