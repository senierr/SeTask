package com.senierr.setasklib;

import com.senierr.setasklib.internal.Executor;
import com.senierr.setasklib.onSubscribes.DelayOnSubscribe;
import com.senierr.setasklib.onSubscribes.IntervalOnSubscribe;
import com.senierr.setasklib.onSubscribes.ObservableOnSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * 订阅事件
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
     * 创建周期执行事件
     *
     * @param delay
     * @param period
     * @param timeUnit
     * @return
     */
    public static Observable<Long> interval(long delay, long period, TimeUnit timeUnit) {
        return create(new IntervalOnSubscribe(delay, period, timeUnit));
    }

    /**
     * 创建延迟执行事件
     *
     * @param delay
     * @param timeUnit
     * @return
     */
    public static Observable<Long> delay(long delay, TimeUnit timeUnit) {
        return create(new DelayOnSubscribe(delay, timeUnit));
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
