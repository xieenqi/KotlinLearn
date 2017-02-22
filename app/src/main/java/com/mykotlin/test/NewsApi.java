package com.mykotlin.test;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by xeq on 17/2/22.
 */

public interface NewsApi {

    @POST("pay/gateway")
    Observable<XMLService> getNewsData(@Body String server, @Body String version, @Body String sign_type, @Body String mch_id);

}
