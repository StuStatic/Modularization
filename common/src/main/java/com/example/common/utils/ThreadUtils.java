package com.example.common.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * fileName：ThreadUtils
 *
 * @author ：qyh
 * date    ：2019-11-22 20:22
 * describe：
 */
public class ThreadUtils {
    public static final String TAG = "ThreadUtils";
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public ThreadUtils() {
    }

    public static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void doOnIOThread(Action action) {
        Observable.create((e) -> {
            action.run();
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @SuppressLint("CheckResult")
    public static<T, R> void doOnIOThread(Function<T, R> function1, T t1,
                                          Function<T, R> function2, T t2,
                                          BiFunction<R, R, Object> biFunction,
                                          Consumer<Object> consumer, Consumer<Throwable> consumerErr) {
        Observable<R> observable1 = opDb(function1, t1).subscribeOn(Schedulers.io());
        Observable<R> observable2 = opDb(function2, t2).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, biFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, consumerErr);
    }

    public static void doAsyncTask(Action action, Consumer consumer) {
        Observable.create((e) -> {
            action.run();
            e.onNext(Boolean.TRUE);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public static <T, R> Observable<R> opDb(Function<T, R> function, T t) {
        return Observable.create((e) -> {
            e.onNext(function.apply(t));
        });
    }
}
