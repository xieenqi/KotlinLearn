package com.router

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chenenyu.router.Router
import kotlinx.android.synthetic.main.activity_router.*
//http://blog.csdn.net/u011404670/article/details/54883263
class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router)
        bit1.setOnClickListener { Router.build("test").go(baseContext)
        Log.d("log","34567890-")
        }
    }
}
