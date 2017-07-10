package com.senierr.setasklib.internal;

import com.senierr.setasklib.Observer;
import com.senierr.setasklib.ObservableOnSubscribe;
import com.senierr.setasklib.scheduler.SchedulerHelper;
import com.senierr.setasklib.scheduler.Schedulers;

/**
 * 执行器
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public class Executor<T> implements Runnable {

    private volatile boolean isCancel = false;

    private Thread thread;

    private Schedulers subscribeScheduler = Schedulers.THREAD;
    private Schedulers observerScheduler = Schedulers.MAIN;
    private ObservableOnSubscribe<T> observableOnSubscribe;
    private Observer<T> observer;

    @Override
    public void run() {
        thread = Thread.currentThread();
        Emitter<T> emitter = new Emitter<>(this);
        try {
            observableOnSubscribe.subscribe(emitter);
            emitter.onComplete();
        } catch (Exception e) {
            emitter.onError(e);
        }
    }

    /**
     * 开始订阅执行
     */
    public void executeSubscribe() {
        SchedulerHelper.getInstance().doOn(subscribeScheduler, this);
    }

    /**
     * 开始订阅执行
     */
    public void executeObserver(Runnable runnable) {
        if (!isCancel) {
            SchedulerHelper.getInstance().doOn(observerScheduler, runnable);
        }
    }

    /**
     * 判断是否解除订阅
     *
     * @return
     */
    public boolean isCancel() {
        return isCancel;
    }

    /**
     * 解除订阅，并试图关闭订阅事件
     */
    public void cancel() {
        isCancel = true;
        observer = null;
        if (subscribeScheduler == Schedulers.THREAD) {
            if (thread != null) {
                thread.interrupt();
            }
        }
    }

    public ObservableOnSubscribe<T> getObservableOnSubscribe() {
        return observableOnSubscribe;
    }

    public void setObservableOnSubscribe(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.observableOnSubscribe = observableOnSubscribe;
    }

    public Observer<T> getObserver() {
        return observer;
    }

    public void setObserver(Observer<T> observer) {
        this.observer = observer;
    }

    public Schedulers getSubscribeScheduler() {
        return subscribeScheduler;
    }

    public void setSubscribeScheduler(Schedulers subscribeScheduler) {
        this.subscribeScheduler = subscribeScheduler;
    }

    public Schedulers getObserverScheduler() {
        return observerScheduler;
    }

    public void setObserverScheduler(Schedulers observerScheduler) {
        this.observerScheduler = observerScheduler;
    }
}