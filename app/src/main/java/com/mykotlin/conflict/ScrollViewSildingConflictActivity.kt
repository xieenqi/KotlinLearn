package com.mykotlin.conflict

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.baidu.mapapi.SDKInitializer
import com.mykotlin.R
import kotlinx.android.synthetic.main.activity_scroll_view_silding_conflict.*
import java.util.*

/*
* 此页页面 解决 scrollView 的滑动冲突
* */
class ScrollViewSildingConflictActivity : AppCompatActivity() {

    private var mLastX = 0.0f
    private var mListLastY = 0.0f
    private var mMapLastY = 0.0f
    private var isScrollBottom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_scroll_view_silding_conflict)
        var pagerAdapter = ViewPageAdapter(this, getPagerData())
        pager.setAdapter(pagerAdapter)
        list.setAdapter(ListAdapter(this, getListData()))
        sidingAction()
    }

    fun getPagerData(): List<String> {
        var pageData = ArrayList<String>()
        pageData.add("p656")
        pageData.add("pp656")
        pageData.add("ppp656")
        pageData.add("pppp656")
        pageData.add("ppppp656")
        return pageData;
    }

    fun getListData(): List<String> {
        val lData = ArrayList<String>()
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168")
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168")
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168")
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168")
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");
        lData.add("168");lData.add("168");lData.add("168");lData.add("168");lData.add("168");
        return lData
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
        pager.setOnTouchListener({ view: View, motionEvent: MotionEvent ->
            when (motionEvent!!.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 记录点击到ViewPager时候，手指的X坐标
                    mLastX = motionEvent.x
                }
                MotionEvent.ACTION_MOVE -> {
                    //超过设定发值
                    if (Math.abs(motionEvent.x - mLastX) > 60f) {
                        refresh.setEnabled(false)
                        scroll.requestDisallowInterceptTouchEvent(true)//要求 禁止拦截触摸事件
                    }
                }
                MotionEvent.ACTION_UP -> {
                    scroll.requestDisallowInterceptTouchEvent(false)
                    refresh.setEnabled(true)
                }
            }
            false
        })
        scroll.setListener(object : ScrollBottomView.onScrollListener {
            override fun onScrollTop() {
                isScrollBottom = false;
            }

            override fun onScrollMiddle() {
                isScrollBottom = false
            }

            override fun onScrollBottom() {
                isScrollBottom = true
            }

        })
        /* 触摸地图 拦截scrollview 的事件 反之不拦截 事件 */
        bMapView.getChildAt(0).setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    scroll.requestDisallowInterceptTouchEvent(false)
                    mMapLastY = motionEvent.y
                }
                MotionEvent.ACTION_MOVE -> {
                    var nowY = motionEvent.y
                    if (Math.abs(mMapLastY - nowY) > bMapView.height * 2 / 5) {
                        scroll.requestDisallowInterceptTouchEvent(false)
                    } else {
                        scroll.requestDisallowInterceptTouchEvent(true)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    scroll.requestDisallowInterceptTouchEvent(true)
                }
            }
            false
        }

        list.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    /*记录 触摸list的y的位置点*/
                    mListLastY = motionEvent.y
                }
                MotionEvent.ACTION_MOVE -> {
                    /* list 顶部位置 */
                    var listTop = list.getChildAt(0).top
                    //当前位置
                    var listNowY = motionEvent.y
                    Log.d("log", "list滚动位置-> " + listTop)
                    /* scrollView 没有滑动到底部  拦截触摸事件*/
                    if (!isScrollBottom) {
                        scroll.requestDisallowInterceptTouchEvent(false)
                        /*list滑动到顶部 并且 当前位置和开始触摸位置大于最大阈值  拦截触摸事件*/
                    } else if (listTop == 0 && listNowY - mListLastY > 20F) {
                        scroll.requestDisallowInterceptTouchEvent(false)
                    } else {
                        //禁止拦截触摸事件
                        scroll.requestDisallowInterceptTouchEvent(true)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    scroll.requestDisallowInterceptTouchEvent(true)
                }
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bMapView.onPause()
    }
}
