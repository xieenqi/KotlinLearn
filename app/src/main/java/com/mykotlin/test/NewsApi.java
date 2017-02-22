package com.mykotlin.test;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by xeq on 17/2/22.
 */

public interface NewsApi {

    @POST("pay/gateway")
    Call<XMLService> getNewsData(@Body HashMap<String, Object> body, Callback<XMLService> cb);

//    service:cibalipay
//    mch_id:1791
//    Key:7378bbb54c3d4de0927bbf4eee769560
    @GET("pay/orderquery")
    Observable<XMLService> getNewsData(@Query("mch_id") String mch_id, @Query("service") String service
            , @Query("Key") String datePre);
}
