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
import com.mykotlin.rxjava.TestRcjavaFlowableActivity;
//import com.router.RouterActivity;

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
    private Button bt03, bt04, bt05, bt06, bt07, bt08, bt09, bt10, bt11;

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
        bt08 = (Button) findViewById(R.id.bt08);
        bt08.setOnClickListener(this);
        bt09 = (Button) findViewById(R.id.bt09);
        bt09.setOnClickListener(this);
        bt10 = (Button) findViewById(R.id.bt10);
        bt10.setOnClickListener(this);
        bt11 = (Button) findViewById(R.id.bt11);
        bt11.setOnClickListener(this);
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
            case R.id.bt08:
                startActivity(new Intent(MainActivity.this, TestRcjavaFlowableActivity.class));
                break;
            case R.id.bt09:
                startActivity(new Intent(MainActivity.this, InputShowHideActivity.class));
                break;
            case R.id.bt10:
                startActivity(new Intent(MainActivity.this, WebLineActivity.class));
                break;
            case R.id.bt11:
//                startActivity(new Intent(MainActivity.this, RouterActivity.class));
                break;
        }
    }
}
