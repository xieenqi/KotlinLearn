package com.router.interceptor

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.router.RouterActivity

/**
 * Created by qiqi on 17/11/2.
 */
@Interceptor(priority = 7)
class TestInterceptor : IInterceptor {
    @JvmField
    var myContext: Context? = null
    @JvmField
    var isInterceptor = false

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        if ("/test/interceptor" == postcard!!.getPath()) {
            isInterceptor = !isInterceptor
//            if (isInterceptor) {
//
//                Toast.makeText(myContext, "action ---------------被拦截-----------------", Toast.LENGTH_LONG).show()
//                Log.d("log", "action ---------------被拦截-----------------")
//                callback!!.onContinue(null)
//            } else {
            postcard.withString("extra", "我是在拦截器中附加的参数")
            callback!!.onContinue(postcard)
//            }
        } else {
            callback!!.onContinue(postcard)
        }
    }

    override fun init(context: Context?) {
        myContext = context
    }
}