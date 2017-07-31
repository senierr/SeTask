package com.senierr.setasklib.onSubscribes;

import com.senierr.setasklib.internal.Emitter;

/**
 * 订阅触发接口
 *
 * @author zhouchunjie
 * @date 2017/6/8
 */

public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter) throws Exception;
}
