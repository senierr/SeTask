package com.senierr.setasklib.onSubscribes;

import com.senierr.setasklib.ObservableOnSubscribe;
import com.senierr.setasklib.internal.Emitter;

/**
 * 间隔订阅事件
 *
 * @author zhouchunjie
 * @date 2017/6/9
 */

public class TimerOnSubscribe implements ObservableOnSubscribe<Long> {

    private long delayMillis;
    private long periodMillis;

    public TimerOnSubscribe(long delayMillis, long periodMillis) {
        this.delayMillis = delayMillis;
        this.periodMillis = periodMillis;
    }

    @Override
    public void subscribe(Emitter<Long> emitter) throws Exception {
        Thread.sleep(delayMillis);
        long index = 0;
        while (!emitter.isCancel()) {
            emitter.onProcess(index);
            index++;
            Thread.sleep(periodMillis);
        }
    }
}
