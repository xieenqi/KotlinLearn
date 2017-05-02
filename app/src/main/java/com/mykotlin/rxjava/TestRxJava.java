package com.mykotlin.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava2 功能测试
 * Created by qiqi on 17/5/1.
 */

public class TestRxJava {
    static TestRxJava instance;

    public static void main(String[] args) {
        instance = new TestRxJava();
        instance.testCreate();
    }

    private void testCreate() {
        //Observable的创建：  {被观察着}
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                //执行一些其他操作
//                //.............
//                //执行完毕，触发回调，通知观察者
//                e.onNext("我来发射数据");
//            }
//        });
        Observable<String> observable = Observable.just("just 创建");
        //Observer的创建： {观察者}
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("是否订阅了相关内容   -> " + d.isDisposed());
            }

            @Override
            //观察者接收到通知,进行相关操作
            public void onNext(Long aLong) {
                System.out.println("我接收到数据了  -> " + aLong);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("我接收到 错误信息  -> " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        //被观察者  订阅 观察者
//        observable.subscribe(observer);
//        testFromIterable().subscribe(observer);
//        testDefer().subscribe(observer);
        testInterval().subscribe(observer);
    }

    /*遍历 数组*/
    private Observable testFromIterable() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }
        Observable<String> observable = Observable.fromIterable((Iterable<String>) list);
        return observable;
    }

    /*当满足回调条件后，就会进行相应的回调。*/
    private Observable testDefer() {

        Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("11");
            }
        });
        return observable;
    }

    private synchronized Observable testInterval() {
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
//        上述表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
//        Observable<Integer> observable = Observable.range(1, 20);
        //它在一个给定的延迟后发射一个特殊的值，即表示延迟2秒后，调用onNext()方法。
//        Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS);
        return observable;
    }
}
