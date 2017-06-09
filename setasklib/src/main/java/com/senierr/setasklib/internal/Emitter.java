package com.senierr.setasklib.internal;

import com.senierr.setasklib.Observer;

/**
 * 分发器
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public class Emitter<T> extends Observer<T> {

    private Executor<T> executor;

    public Emitter(Executor<T> executor) {
        this.executor = executor;
    }

    @Override
    public void onProcess(final T value) {
        executor.executeObserver(new Runnable() {
            @Override
            public void run() {
                if (executor.getObserver() != null) {
                    executor.getObserver().onProcess(value);
                }
            }
        });
    }

    @Override
    public void onComplete() {
        executor.executeObserver(new Runnable() {
            @Override
            public void run() {
                if (executor.getObserver() != null) {
                    executor.getObserver().onComplete();
                }
                executor.cancel();
            }
        });
    }

    @Override
    public void onError(final Exception e) {
        executor.executeObserver(new Runnable() {
            @Override
            public void run() {
                if (executor.getObserver() != null) {
                    executor.getObserver().onError(e);
                }
                executor.cancel();
            }
        });
    }

    /**
     * 判断是否解除订阅
     *
     * @return
     */
    public boolean isCancel() {
        return executor.isCancel();
    }
}
