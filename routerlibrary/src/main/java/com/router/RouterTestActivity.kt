package com.router

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chenenyu.router.annotation.Route

@Route("test")
class RouterTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router_test)
    }
}
