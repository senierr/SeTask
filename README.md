# SeTask

[![](https://jitpack.io/v/senierr/SeTask.svg)](https://jitpack.io/#senierr/SeTask)

学习RxJava的平滑过渡库，主要用于Android场景，功能类似AsyncTask和Single。

## 基本使用

### 1. 导入仓库

```java
maven { url 'https://jitpack.io' }
```
### 2. 添加依赖

```java
compile 'com.github.senierr:SeTask:1.0.0'
```

### 普通订阅

```java
Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
    @Override
    public void subscribe(Emitter<String> emitter) throws Exception {
        emitter.isCancel();                         // 判断订阅是否终止
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

### 计时订阅

```java
Observable.timer(delayMillis, periodMillis)
        .bindToObservatory(observatory)
        .subscribe(new Observer<Long>() {
            @Override
            public void onProcess(Long value) {
                log("onProcess: " + value);
            }
        });
```

### 延迟订阅

```java
Observable.delay(delayMillis)
        .bindToObservatory(observatory)
        .subscribe(new Observer<Long>() {
            @Override
            public void onComplete() {
                log("onComplete");
            }
        });
```

## 取消订阅

* 单个取消订阅

```java
observable.cancel();
```

* 批量取消订阅

```java
// 创建观察站
Observatory observatory = new Observatory();
// 绑定至观察站
observatory.bindToObservatory(observatory)
// 批量取消订阅
observatory.cancelAll();
```