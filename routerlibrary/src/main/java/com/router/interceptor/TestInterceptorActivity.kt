package com.router.interceptor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.router.R

/*路由 框架 拦截测试*/
@Route(path = "/test/interceptor")
class TestInterceptorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_interceptor)
        Toast.makeText(this, intent.getStringExtra("extra"), Toast.LENGTH_LONG).show()
    }
}
