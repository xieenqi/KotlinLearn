package com.router

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_router.*

//https://github.com/alibaba/ARouter  阿里巴巴开源

@Route(path = "/router/kotlin")
class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router)
        bit1.setOnClickListener {
            ARouter.getInstance().build("/test/kotlin").navigation()
            Log.d("log", "34567890-")
        }
    }
}
