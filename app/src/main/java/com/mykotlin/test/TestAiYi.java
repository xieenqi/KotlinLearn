package com.mykotlin.test;

import android.util.Log;
import android.widget.Toast;

import com.mykotlin.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by xeq on 17/2/22.
 */

public class TestAiYi {
    //请求 url:https://www.iyibank.com/pay/gateway
    public static void main(String[] args) {
        System.out.println("5678----------------------------90-=");

        getData();
    }

    public static <T> T createByXML(String baseUrl, Class<T> service) {
        OkHttpClient client = new OkHttpClient();

//        Request request = new Request.Builder()
//                .url("http://www.baidu.com")
//                .header("User-Agent", "OkHttp Headers.java")
//                .build();

//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    private static void getData() {
        NewsApi api = createByXML("https://www.iyibank.com/", NewsApi.class);
        api.getNewsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<XMLService>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("请求完成---》onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("请求出错---》onError  " + e.toString());
                    }

                    @Override
                    public void onNext(XMLService xmlService) {
                        System.out.println("请求cg成功下一步---》onNext ");
                    }
                });
    }

}
