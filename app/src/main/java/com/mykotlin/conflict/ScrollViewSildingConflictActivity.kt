package com.mykotlin.conflict

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import com.mykotlin.R
import kotlinx.android.synthetic.main.activity_scroll_view_silding_conflict.*
import java.util.*

/*
* 此页页面 解决 scrollView 的滑动冲突
* */
class ScrollViewSildingConflictActivity : AppCompatActivity() {

    private var mLastX = 0.0f;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_silding_conflict)
        var pagerAdapter = ViewPageAdapter(this, getPagerData())
        pager.setAdapter(pagerAdapter)
        sidingAction()
    }

    fun getPagerData(): List<String> {
        var pageData = ArrayList<String>();
        pageData.add("p656")
        pageData.add("pp656")
        pageData.add("ppp656")
        pageData.add("pppp656")
        pageData.add("ppppp656")
        return pageData;
    }

    /**
     * 滑动 动作处理
     */
    fun sidingAction() {
        //四秒过后 刷新完成
        refresh.setOnRefreshListener {
            Handler().postDelayed({ refresh.setRefreshing(false) }, 4000)
        }
        /*解决viewPager 与 刷新空间之间的冲突 */
        pager.setOnTouchListener(View.OnTouchListener() { view: View, motionEvent: MotionEvent ->
            when (motionEvent!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 记录点击到ViewPager时候，手指的X坐标
                    mLastX = motionEvent.x
                }
                MotionEvent.ACTION_MOVE -> {
                    //超过设定发值
                    if (Math.abs(motionEvent.x - mLastX) > 60f) {
                        refresh.setEnabled(false)
                        scroll.requestDisallowInterceptTouchEvent(true)//要求禁止拦截触摸事件
                    }
                }
                MotionEvent.ACTION_UP -> {
                    scroll.requestDisallowInterceptTouchEvent(false)
                    refresh.setEnabled(true)
                }
            }
            false
        })
    }

}
