package com.mykotlin.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mykotlin.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 文档地址
 * https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Just.html
 */


public class TestRcjavaFlowableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rcjava_flowable);
        testFlowable();
    }

    private void testTheard() {
             /*rxjava2 线程切换 演示*/
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("hou-所在线程:-> " + Thread.currentThread());
                        System.out.println("hou-发送的数据:" + "integer--" + integer);
                    }
                });
    }


    private void testFlowable() {
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
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//
//                    @Override
//                    public void accept(@NonNull Object o) throws Exception {
//                        Thread.sleep(2000);
//                        System.out.println("被压接受: " + o);
//                    }
//                });
   /*应用场景多次提交数据 只响应一次*/
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d("log", "emit 1");
                emitter.onNext(1);
                Log.d("log", "emit 2");
                emitter.onNext(2);
                Log.d("log", "emit 3");
                emitter.onNext(3);
                Log.d("log", "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR); //增加了一个参数

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d("log", "onSubscribe");
                s.request(2);//回传接受的
//                这个方法就是用来向生产者申请可以消费的事件数量。这样我们便可以根据本身的消费能力进行消费事件。
//                当调用了request()方法后，生产者便发送对应数量的事件供消费者消费。
//                这是因为Flowable在设计的时候采用了一种新的思路也就是响应式拉取的方式,你要求多少，我便传给你多少。

//                虽然并不限制向request()方法中传入任意数字，但是如果消费者并没有这么多的消费能力，依旧会造成资源浪费，最后产生OOM。形象点就是不能打肿脸充胖子。
//                而ERROR策略就避免了这种情况的出现(讲了这么多终于出现了)。
//
//                在异步调用时，RxJava中有个缓存池，用来缓存消费者处理不了暂时缓存下来的数据，缓存池的默认大小为128，即只能缓存128个事件。无论request()中传入的数字比128大或小，缓存池中在刚开始都会存入128个事件。当然如果本身并没有这么多事件需要发送，则不会存128个事件。
//                在ERROR策略下，如果缓存池溢出，就会立刻抛出MissingBackpressureException异常。
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("log", "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w("log", "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d("log", "onComplete");
            }
        };
        flowable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
