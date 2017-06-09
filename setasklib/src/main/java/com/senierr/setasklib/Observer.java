package com.senierr.setasklib;

/**
 * 观察者
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public abstract class Observer<T> {

    public void onProcess(T value) {}

    public void onComplete() {}

    public void onError(Exception e) {}
}
