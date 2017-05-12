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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
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
        instance.testMapFlatmap();
//        instance.testConsumer();

//        instance.testThread();
//        instance.testFlowable();
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
//        Observable<String> observable = Observable.just("just 创建", "1", "2");
        //Observer的创建： {观察者}
        Observer<Object> observer = new Observer<Object>() {
            //Disposable相当于RxJava1.x中的Subscription,用于解除订阅。
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("是否 切断 被观察者和观察者的链接   -> " + d.isDisposed());
                disposable = d;
//                注意：当切断被观察者与观察者之间的联系，Observable(被观察者)的事件却仍在继续执行。
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
//        testMapFlatmap().subscribe(observer);

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
//        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
//        表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
        Observable<Integer> observable = Observable.range(1, 20);
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

    private void test() {
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                System.out.println(aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
//        SystemClock.sleep(10000);//睡眠10秒后，才进行订阅  仍然从0开始，表示Observable内部逻辑刚开始执行
        observable.subscribe(observer);
    }


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

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object s) throws Exception {
                //设置 过滤条件
                String newStr = (String) s;
                if (newStr.length() > 6) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String) o);
            }
        });

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
            //最多输出多少个
        }).take(13).doOnNext(new Consumer<Object>() {
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
//        ● Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
//
//        ●Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
//
//        ●Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
// 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是用一个无数量上限的线程池，可以重用空闲的线程，
// 因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
//
//        ●Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，
// 即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，
// 大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
//
//        ● Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                System.out.println("qian所在线程:-> " + Thread.currentThread());
//                System.out.println("qian发送的数据:" + 1 + "");
//                e.onNext(1);
//            }
////            subscribeOn(): 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程。
//// F* observeOn(): 指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程。
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        System.out.println("hou-所在线程:-> " + Thread.currentThread());
//                        System.out.println("hou-发送的数据:" + "integer--" + integer);
//                    }
//                });
    }


    private void testFlowable() {
//        注意：处理Backpressure的策略仅仅是处理Subscriber接收事件的方式，并不影响Flowable发送事件的方法。
//        即使采用了处理Backpressure的策略，Flowable原来以什么样的速度产生事件，现在还是什么样的速度不会变化，
//        主要处理的是Subscriber接收事件的方式。
//        Observable.create(new ObservableOnSubscribe<Object>() {
//
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
//                while (true) {
//                    e.onNext(1);
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .subscribe(new Consumer<Object>() {
//
//                    @Override
//                    public void accept(@NonNull Object o) throws Exception {
//                        Thread.sleep(2000);
//                        System.out.println("被压接受: " + o);
//                    }
//                });
    }

}
