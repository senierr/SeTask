package com.senierr.setasklib.onSubscribes;

import com.senierr.setasklib.ObservableOnSubscribe;
import com.senierr.setasklib.internal.Emitter;

/**
 * 间隔订阅事件
 *
 * @author zhouchunjie
 * @date 2017/6/9
 */

public class DelayOnSubscribe implements ObservableOnSubscribe<Long> {

    private long delayMillis;

    public DelayOnSubscribe(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public void subscribe(Emitter<Long> emitter) throws Exception {
        Thread.sleep(delayMillis);
        emitter.onProcess(0L);
    }
}
