package com.mykotlin;

import android.app.Activity;
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
import com.mykotlin.test.TestAiYi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.configChanges;
import static com.facebook.stetho.inspector.protocol.module.Console.MessageSource.NETWORK;

public class MainActivity extends Activity {
    private QuickKV quickKv;
    private int index = 100000;
    private long startTime1, endTime1, startTime2, endTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quickKv = new QuickKV(MainActivity.this);
        findViewById(R.id.sendKotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                TestAiYi.getData();
            }
        });
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

}
