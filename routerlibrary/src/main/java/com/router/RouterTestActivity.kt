package com.router

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_router_test.*

@Route(path = "/test/kotlin")
class RouterTestActivity : AppCompatActivity() {

    @Autowired
    @JvmField
    var textInfo = "34567890-======="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router_test)
        bit1.setOnClickListener { ARouter.getInstance().build("/test/java").navigation() }
        ARouter.getInstance().inject(this)
        t1.text = textInfo


        var intent = Intent()
        intent.putExtra("12", "23")
        setResult(55, intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        finish()
       ActivityCompat.finishAfterTransition(this)
    }
}
