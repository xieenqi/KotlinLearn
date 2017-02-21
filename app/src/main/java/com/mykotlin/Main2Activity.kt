package com.mykotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.mykotlin.ben.KotlinTest2
import com.mykotlin.ben.TestKotlin
import com.mykotlin.utils.Coordinate
import com.mykotlin.utils.Preference
import kotlinx.android.synthetic.main.activity_main2.*
import java.net.URL

class Main2Activity : AppCompatActivity() {
    var TAG = "kotlin"
    var pag: Int by Preference(this, "pag", 0)
    var coord= Coordinate(5.0,20.0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)
        sendKotlin.textSize = 20.0f
        sendKotlin.setTextColor(resources.getColor(R.color.colorAccent))
        sendKotlin.text = setText()
        sendKotlin.setOnClickListener {
//            intent = Intent()
//            intent.setClassName(this, "com.mykotlin.MainActivity")
//            startActivity(intent)

            Log.d("kotlin","nvsd不是滴吧和v "+pag)
            pag = 200556
//            Log.d("kotlin",coord.theta+"  扩展类 "+coord.R())
        }
//        Log.d("kotlin","nvsd不是滴吧和v "+sendKotlin2)
    }

    fun setText(): String {
        return TestKotlin().info + "kotlin 方法值的传递!!!!" + KotlinTest2().tt;
    }

    private val sendKotlin2: TextView by lazy { findViewById(R.id.sendKotlin2) as TextView }
}


