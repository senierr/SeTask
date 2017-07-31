package com.senierr.setasklib.onSubscribes;

import com.senierr.setasklib.internal.Emitter;

import java.util.concurrent.TimeUnit;

/**
 * 间隔订阅事件
 *
 * @author zhouchunjie
 * @date 2017/6/9
 */

public class IntervalOnSubscribe implements ObservableOnSubscribe<Long> {

    private long delay;
    private long period;
    private TimeUnit timeUnit;

    private long index = 0;

    public IntervalOnSubscribe() {
    }

    public IntervalOnSubscribe(long delay, long period, TimeUnit timeUnit) {
        this.delay = delay;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public void subscribe(Emitter<Long> emitter) throws Exception {
        if (!emitter.isCancel()) {
            emitter.onProcess(index);
            index++;
        }
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
