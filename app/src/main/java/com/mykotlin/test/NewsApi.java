package com.mykotlin.test;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
/**
 * Created by xeq on 17/2/22.
 */

public interface NewsApi {

    @POST("pay/gateway")
    Observable getNewsData();

}
