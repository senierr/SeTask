package com.senierr.setasklib;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量管理被观察者
 *
 * @author zhouchunjie
 * @date 2017/6/9
 */

public class Observatory {

    private List<Observable> observables;

    public Observatory() {
        observables = new ArrayList<>();
    }

    /**
     * 添加被观察者进管理
     *
     * @param observable
     */
    public void add(Observable observable) {
        observables.add(observable);
    }

    /**
     * 清除管理列表，但不解除订阅
     */
    public void clear() {
        observables.clear();
    }

    /**
     * 解除所有订阅
     */
    public void cancelAll() {
        for (Observable observable : observables) {
            observable.cancel();
        }
    }
}
