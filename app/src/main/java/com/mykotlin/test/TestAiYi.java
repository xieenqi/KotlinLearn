package com.mykotlin.test;

import android.util.Log;
import android.widget.Toast;

import com.mykotlin.R;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by xeq on 17/2/22.
 */

public class TestAiYi {

    static NewsApi newsApi = new Retrofit.Builder().baseUrl("https://www.iyibank.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(NewsApi.class);

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
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.31.242:8080/springmvc_users/user/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        IUserBiz userBiz = retrofit.create(IUserBiz.class);
//        Call<List<User>> call = userBiz.getUsers();
//        call.enqueue(new Callback<List<User>>()
//        {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response)
//            {
//                Log.e(TAG, "normalGet:" + response.body() + "");
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t)
//            {
//
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("server", "cibalipay");
        map.put("version", "1.0");
        map.put("sign_type", "MD5");
        map.put("mch_id", "1791");
        map.put("out_trade_no", "订单编号1");
        map.put("body", "商品描述---");
        map.put("total_fee", 100.0);
        map.put("mch_create_ip", "1611");
        map.put("notify_url", "http://wap.tenpay.com/t enpay.asp");
        map.put("nonce_str", "随机字符串,不长于 32 位");
//        map.put("sign","");


        newsApi.postNewsData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<XMLService, Observable<String>>() {
                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                        System.out.println("请求post出错---》onError  ");
                    }

                    @Override
                    public Observable<String> call(XMLService xmlService) {
                        System.out.println(xmlService + "post请求成功下一步->message " + xmlService.message
                                + "  status-->" + xmlService.status);
                        return Observable.from(new String[]{xmlService.message});
                    }
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("请求post出错---》" + e.toString());
            }

            @Override
            public void onNext(String s) {
                System.out.println("请求post  onNext》" + s.toString());
            }
        });
    }

    public static void getData2() {
        newsApi.getNewsData("cibalipay", "1791", "7378bbb54c3d4de0927bbf4eee769560")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<XMLService, Observable<String>>() {
                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                        System.out.println("请求222---33333出错---》onError  ");
                    }

                    @Override
                    public Observable<String> call(XMLService xmlService) {
                        System.out.println("请求成功下一步->message " + xmlService.message
                                + "  status-->" + xmlService.status);
                        return Observable.from(new String[]{xmlService.message});
                    }
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("请求成功下一步---》onCompleted ");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("请求成功下一步---》onError ");
            }

            @Override
            public void onNext(String s) {
                System.out.println("请求成功下一步---》 onNext");
            }
        });
    }
}



