package com.senierr.setask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.senierr.setasklib.internal.Emitter;
import com.senierr.setasklib.Observable;
import com.senierr.setasklib.Observatory;
import com.senierr.setasklib.ObservableOnSubscribe;
import com.senierr.setasklib.Observer;
import com.senierr.setasklib.scheduler.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Observatory observatory = new Observatory();
    private Observable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normal();
//        timer();
//        delay();
    }

    @Override
    protected void onDestroy() {
//        observable.cancel();
        observatory.cancelAll();
        super.onDestroy();
    }

    private void normal() {
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Emitter<String> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    log("--subscribe: " + i);
                    emitter.onProcess("onProcess: " + i);
                    if (i == 6) {
//                        throw new Exception("Test cancel!");
                        emitter.onComplete();
                    }
//                    Thread.sleep(1000);
//                    SystemClock.sleep(1000);
                }
            }
        })
                .subscribeOn(Schedulers.THREAD)
                .observerOn(Schedulers.MAIN)
                .bindToObservatory(observatory)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onProcess(String value) {
                        log(value);
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete");
                    }

                    @Override
                    public void onError(Exception e) {
                        log("onError: " + e.toString());
                    }
                });
    }

    private void timer() {
        Observable.timer(1000, 1000)
                .bindToObservatory(observatory)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onProcess(Long value) {
                        log("onProcess: " + value);
                    }
                });
    }

    private void delay() {
        Observable.delay(5000)
                .bindToObservatory(observatory)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onComplete() {
                        log("onComplete");
                    }
                });
    }

    private void log(String message) {
        if (!TextUtils.isEmpty(message)) {
            Log.e("MainActivity", message);
        }
    }
}
