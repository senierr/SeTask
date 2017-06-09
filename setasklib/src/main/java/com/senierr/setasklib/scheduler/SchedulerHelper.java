package com.senierr.setasklib.scheduler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
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

    private Executor threadScheduler;
    private Handler mainScheduler;

    private static SchedulerHelper schedulerHelper;

    private SchedulerHelper() {
        threadScheduler
                = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        mainScheduler = new Handler(Looper.getMainLooper());
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
     * 线程执行
     *
     * @param scheduler
     * @param runnable
     */
    public void doOn(Schedulers scheduler, Runnable runnable) {
        switch (scheduler) {
            case MAIN:
                mainScheduler.post(runnable);
                break;
            case THREAD:
                threadScheduler.execute(runnable);
                break;
        }
    }
}
