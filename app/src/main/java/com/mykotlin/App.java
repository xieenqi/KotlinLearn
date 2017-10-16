package com.mykotlin;

import android.app.Application;

import com.chenenyu.router.Router;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by xeq on 17/3/16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        // 初始化
        Router.initialize(this);
        // 开启log
        if (BuildConfig.DEBUG) {
            Router.setDebuggable(true);
        }

    }
}
