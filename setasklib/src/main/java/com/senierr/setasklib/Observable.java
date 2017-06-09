package com.senierr.setasklib;

import com.senierr.setasklib.internal.Executor;
import com.senierr.setasklib.onSubscribes.DelayOnSubscribe;
import com.senierr.setasklib.onSubscribes.TimerOnSubscribe;
import com.senierr.setasklib.scheduler.Schedulers;

/**
 * 被观察者
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public class Observable<T> {

    private Executor<T> executor;

    private Observable(ObservableOnSubscribe<T> observableOnSubscribe) {
        executor = new Executor<>();
        executor.setObservableOnSubscribe(observableOnSubscribe);
    }

    /**
     * 创建自定义执行事件
     *
     * @param observableOnSubscribe
     * @param <T>
     * @return
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        return new Observable<>(observableOnSubscribe);
    }

    /**
     * 创建计时器执行事件
     *
     * @param delayMillis
     * @param periodMillis
     * @return
     */
    public static Observable<Long> timer(long delayMillis, long periodMillis) {
        return create(new TimerOnSubscribe(delayMillis, periodMillis));
    }

    /**
     * 创建延迟执行事件
     *
     * @param delayMillis
     * @return
     */
    public static Observable<Long> delay(long delayMillis) {
        return create(new DelayOnSubscribe(delayMillis));
    }

    /**
     * 设置订阅事件线程
     *
     * @param scheduler
     * @return
     */
    public final Observable<T> subscribeOn(Schedulers scheduler) {
        executor.setSubscribeScheduler(scheduler);
        return this;
    }

    /**
     * 设置订阅者线程
     *
     * @param scheduler
     * @return
     */
    public final Observable<T> observerOn(Schedulers scheduler) {
        executor.setObserverScheduler(scheduler);
        return this;
    }

    /**
     * 绑定至观察站
     *
     * @param observatory
     */
    public final Observable<T> bindToObservatory(Observatory observatory) {
        if (observatory != null) {
            observatory.add(this);
        }
        return this;
    }

    /**
     * 开始订阅
     *
     * @param observer
     * @return
     */
    public final Observable<T> subscribe(final Observer<T> observer) {
        executor.setObserver(observer);
        executor.executeSubscribe();
        return this;
    }

    /**
     * 判断是否解除订阅
     *
     * @return
     */
    public boolean isCancel() {
        return executor.isCancel();
    }

    /**
     * 解除订阅，并试图关闭订阅事件
     */
    public void cancel() {
        executor.cancel();
    }
}
