package com.mykotlin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.library.quickkv.QuickKV;
import com.library.quickkv.database.KeyValueDatabase;
import com.mykotlin.ben.KotlinTest2;
import com.mykotlin.conflict.ScrollViewSildingConflictActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {
    private QuickKV quickKv;
    private int index = 100000;
    private long startTime1, endTime1, startTime2, endTime2;
    private Button bt03, bt04, bt05, bt06, bt07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quickKv = new QuickKV(MainActivity.this);
//        findViewById(R.id.sendKotlin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(MainActivity.this, Main2Activity.class));
//                TestAiYi.getData();
//            }
//        });
        ((TextView) findViewById(R.id.sendKotlin)).setText("" + new KotlinTest2().tt);
        final Button bt1 = (Button) findViewById(R.id.bt01);
        Button bt2 = (Button) findViewById(R.id.bt02);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime1 = System.currentTimeMillis();
                //Do the same thing with QuickKV
                //用QuickKV做同样的事情

                KeyValueDatabase pkvdb1 = quickKv.getDatabase("Foo");
                for (int i = 0; i < index; i++) {
                    pkvdb1.put("Key我" + i, "Value123" + i);
                }

                pkvdb1.persist();
                //Done! Saved to local storage!
                //完成！已保存至本地存储器！

                endTime1 = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "写入耗时" + (endTime1 - startTime1) + "ms", Toast.LENGTH_LONG).show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime2 = System.currentTimeMillis();
                //Let try to load this saved database!
                //让我们来试试载入这个保存好的数据库！
                KeyValueDatabase pkvdb2 = quickKv.getDatabase("Foo");
                for (int i = 0; i < index; i++) {
                    pkvdb2.get("Key" + i);
                }
                endTime2 = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, pkvdb2.size() + "--读取耗时" + (endTime2 - startTime2) + "ms", Toast.LENGTH_LONG).show();
                //Output: "Value"
                //输出: "Value"
                Log.d("tag", "数据库路径: " + quickKv.getStorageManager().getWorkspace().getParent());
            }
        });
//        findViewById(android.R.id.content).setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        bt03 = (Button) findViewById(R.id.bt03);
        bt03.setOnClickListener(this);
        bt04 = (Button) findViewById(R.id.bt04);
        bt04.setOnClickListener(this);
        bt05 = (Button) findViewById(R.id.bt05);
        bt05.setOnClickListener(this);
        bt06 = (Button) findViewById(R.id.bt06);
        bt06.setOnClickListener(this);
        bt07 = (Button) findViewById(R.id.bt07);
        bt07.setOnClickListener(this);



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
        testFlowable();
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
                s.request(1);//回传接受的
//                这个方法就是用来向生产者申请可以消费的事件数量。这样我们便可以根据本身的消费能力进行消费事件。
//                当调用了request()方法后，生产者便发送对应数量的事件供消费者消费。
//                这是因为Flowable在设计的时候采用了一种新的思路也就是响应式拉取的方式,你要求多少，我便传给你多少。
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

    //网络拦截
    private void getFromNetwork() {
        OkHttpClient client = new OkHttpClient();
        //下面这句话显得尤为重要，加入后才能拦截到http请求。
        client.networkInterceptors().add(new StethoInterceptor());
        //构建请求
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        Response response = null;

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    //    <!--android:configChanges="orientation|screenSize"  activity 配置了 横竖屏切换 就不会销毁页面 只会执行onConfigurationChanged-->
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("log", "onConfigurationChanged()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("log", "onStart()");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("log", "onResume()");
        int i1 = 1;
        Integer i2 = 1;
        Integer i3 = new Integer(1);
        Log.d("log", "输出结果:" + (i1 == i2));
        Log.d("log", "输出结果:" + (i1 == i3));
        Log.d("log", "输出结果:" + (i2 == i3));


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("log", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("log", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("log", "onDestroy()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt03:
                startActivity(new Intent(MainActivity.this, SpringAnimationActivity.class));
                break;
            case R.id.bt04:
                startActivity(new Intent(MainActivity.this, ScrollViewSildingConflictActivity.class));
                break;
            case R.id.bt05:
                startActivity(new Intent(MainActivity.this, CollapsedTextViewActivity.class));
                break;
            case R.id.bt06:
                startActivity(new Intent(MainActivity.this, ClearEditTextActivity.class));
                break;
            case R.id.bt07:
                startActivity(new Intent(MainActivity.this, ChartActivity.class));
                break;

        }
    }
}
