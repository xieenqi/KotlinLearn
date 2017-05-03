package com.mykotlin.rxjava;

import android.os.SystemClock;
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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava2 功能测试
 * Created by qiqi on 17/5/1.
 */

public class TestRxJava {
    static TestRxJava instance;

    public static void main(String[] args) {
        instance = new TestRxJava();
        instance.testCreate();
//        instance.testConsumer();

        instance.testThread();
    }

    Disposable disposable;

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
        Observable<String> observable = Observable.just("just 创建", "1", "2");
        //Observer的创建： {观察者}
        Observer<Object> observer = new Observer<Object>() {
            //Disposable相当于RxJava1.x中的Subscription,用于解除订阅。
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("是否订阅了相关内容   -> " + d.isDisposed());
                disposable = d;
            }

            @Override
            //观察者接收到通知,进行相关操作
            public void onNext(Object aLong) {
                System.out.println("我接收到数据了  -> " + aLong);
//                if ((int) aLong >= 7)
//                    disposable.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("我接收到 错误信息  -> " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("我接收到  -> " + "onComplete");
            }
        };
        //被观察者  订阅 观察者
//        observable.subscribe(observer);
//        testFromIterable().subscribe(observer);
//        testDefer().subscribe(observer);
//        testInterval().subscribe(observer);
        testMapFlatmap().subscribe(observer);

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

    /*简单 观察着模式  函数式接口 */

    private void testConsumer() {
//        其中Consumer中的accept()方法接收一个来自Observable的单个值。Consumer就是一个观察者。其他函数式接口可以类似应用
        Observable.just(" 开始 测试 testConsumer ").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("testConsumer 我接收到  -> " + s);
            }
        });
    }

//    private void test(){
//        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
//        Observer<Long> observer = new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                System.out.println(aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
////        SystemClock.sleep(10000);//睡眠10秒后，才进行订阅  仍然从0开始，表示Observable内部逻辑刚开始执行
//        observable.subscribe(observer);
//    }


    private Observable testMapFlatmap() {
//        Observable<Integer> observable = Observable.just("hello").map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) throws Exception {
//                return s.length();
//            }
//        });

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            list.add("Hello" + i);
        }
        Observable<Object> observable = Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        });

//        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(List<String> strings) throws Exception {
//                return Observable.fromIterable(strings);
//            }
//        }).filter(new Predicate<Object>() {
//            @Override
//            public boolean test(Object s) throws Exception {
//                //设置 过滤条件
//                String newStr = (String) s;
//                if (newStr.length() > 6) {
//                    return true;
//                }
//                return false;
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                System.out.println((String) o);
//            }
//        });

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
            //最多输出多少个
        }).take(6).doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("doOnNext()允许我们在每次输出一个元素之前做一些额外的事情。");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String) o);
            }
        });
        return observable;
    }

    private void testThread() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("qian所在线程:-> " + Thread.currentThread());
                System.out.println("qian发送的数据:" + 1 + "");
                e.onNext(1);
            }
//            subscribeOn(): 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程。
// F* observeOn(): 指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程。
        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("hou-所在线程:-> " + Thread.currentThread());
                        System.out.println("hou-发送的数据:" + "integer--" + integer);
                    }
                });
    }


}
