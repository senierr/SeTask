# SeTask

RxJava的平滑过渡库，主要用于Android场景；

## 基本使用

* 普通订阅

```java
Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
    @Override
    public void subscribe(Emitter<String> emitter) throws Exception {
        emitter.isCancel();                         // 判断订阅是否结束
        emitter.onProcess("value");                 // 触发处理回调，不会终止订阅
        emitter.onComplete();                       // 触发结束/完成回调，并终止订阅
        emitter.onError(new Exception("Error"));    // 触发异常回调，并终止订阅
    }
})
        .subscribeOn(Schedulers.THREAD)     // 订阅事件线程，默认异步线程
        .observerOn(Schedulers.MAIN)        // 观察者回调线程，默认UI主线程
        .bindToObservatory(observatory)     // 绑定至观察站
        .subscribe(new Observer<String>() {
            @Override
            public void onProcess(String value) {
                // 处理回调
            }

            @Override
            public void onComplete() {
                // 结束/完成回调
            }

            @Override
            public void onError(Exception e) {
                // 异常回调
            }
        });
```

* 计时订阅

```java
Observable.timer(1000, 1000)
        .bindToObservatory(observatory)
        .subscribe(new Observer<Long>() {
            @Override
            public void onProcess(Long value) {
                log("onProcess: " + value);
            }
        });
```

* 延迟订阅

```java
Observable.delay(5000)
        .bindToObservatory(observatory)
        .subscribe(new Observer<Long>() {
            @Override
            public void onComplete() {
                log("onComplete");
            }
        });
```