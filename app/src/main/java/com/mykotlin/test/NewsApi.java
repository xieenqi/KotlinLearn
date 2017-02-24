package com.mykotlin.test;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by xeq on 17/2/22.
 */

public interface NewsApi {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @FormUrlEncoded
    @POST("pay/gateway")
    Observable<XMLService> postNewsData(@FieldMap Map<String, Object> fieldMap);

    //    service:cibalipay
//    mch_id:1791
//    Key:7378bbb54c3d4de0927bbf4eee769560
    @GET("pay/orderquery")
    Observable<XMLService> getNewsData(@Query("mch_id") String mch_id, @Query("service") String service
            , @Query("Key") String datePre);
}
