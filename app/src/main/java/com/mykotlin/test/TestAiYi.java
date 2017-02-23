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
//        map.put("server", "cibalipay");
//        map.put("server", "cibalipay");
//        "cibalipay", "1.0", "MD5", "1791",
        NewsApi api = createByXML("https://www.iyibank.com/", NewsApi.class);
        api.getNewsData(map, new Callback<XMLService>() {
            @Override
            public void onResponse(Call<XMLService> call, retrofit2.Response<XMLService> response) {
                System.out.println("请求cg成功下一步---》 " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<XMLService> call, Throwable t) {
                System.out.println("请求出错---》onError  " + t.getMessage());
            }
        });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<XMLService>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("请求完成---》onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("请求出错---》onError  " + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(XMLService xmlService) {
//                        System.out.println("请求cg成功下一步---》onNext ");
//                    }
//                });


    }

    public static void getData2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("server", "cibalipay");
        map.put("version", "1.0");
        map.put("sign_type", "MD5");
        map.put("mch_id", "1791");
        NewsApi newsApi = new Retrofit.Builder().baseUrl("https://www.iyibank.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(NewsApi.class);


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
                       +"  status-->"+xmlService.status );
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
                System.out.println("请求成功下一步---》 onNext" );
            }
        });
    }
}
//    .flatMap(new Func1<NewsDataXml, Observable<NewsXml>>() {
//
//        @Override
//
//        public Observable<NewsXml> call(NewsDataXml newsDataXml) {
//
//            return Observable.from(newsDataXml.newsXmls);
//
//        }
//
//    }).subscribe(new Subscriber<NewsXml>() {
//
//        @Override
//
//        public void onCompleted() {
//
//            Log.v("xmlidea", "onCompleted");
//
//        }



