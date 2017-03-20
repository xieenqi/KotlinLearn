package com.mykotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_spring_animation.*

/*
* 此页面是 展示弹性动画
* */
class SpringAnimationActivity : AppCompatActivity(), View.OnTouchListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring_animation)
        spring_view.setOnTouchListener(this)

    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                Toast.makeText(this, "按到", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }
}
