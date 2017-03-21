package com.mykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.animation.SpringAnimation
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_spring_animation.*
import android.view.VelocityTracker


/*
* 此页面是 展示弹性动画
* */
class SpringAnimationActivity : AppCompatActivity(), View.OnTouchListener {

    private var downX: Float = 0.toFloat()
    private var downY: Float = 0.toFloat()
    private var velocityTracker: VelocityTracker? = null//速度跟踪 得到伪速度

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring_animation)
        spring_view.setOnTouchListener(this)
        velocityTracker = VelocityTracker.obtain()
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
//                Toast.makeText(this, "按到", Toast.LENGTH_SHORT).show()
                downX = event.x
                downY = event.y
                velocityTracker!!.addMovement(event)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                spring_view!!.translationX = (event.x - downX)
                spring_view!!.translationY = (event.y - downY)
                velocityTracker!!.addMovement(event)
                return true
            }

            MotionEvent.ACTION_UP -> {
                velocityTracker!!.computeCurrentVelocity(1000)//计算当前速度
                if (spring_view.translationX != 0.0F) {
                    var animX = SpringAnimation(spring_view, SpringAnimation.TRANSLATION_X, 0f)
                    animX.spring.setStiffness(getStiffness())//刚性程度
                    animX.spring.setDampingRatio(getDamping())//阻尼效果
                    animX.setStartVelocity(velocityTracker!!.xVelocity)
                    animX.start()
                }
                if (spring_view.translationY != 0.0F) {
                    val animY = SpringAnimation(spring_view, SpringAnimation.TRANSLATION_Y, 0f)
                    animY.spring.setDampingRatio(getDamping())
                    animY.spring.setStiffness(getStiffness())
                    animY.setStartVelocity(velocityTracker!!.yVelocity)
                    animY.start()
                }
                velocityTracker!!.clear()
                return true
            }
        }
        return false
    }

    fun getStiffness(): Float {
        return Math.max((Stiffne.progress).toFloat(), 1f);
    }

    fun getDamping(): Float {
        return Damping.progress / 100f;
    }
}
