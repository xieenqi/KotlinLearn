package com.mykotlin.webline

import android.app.Activity
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.widget.TextView
import java.util.*

/**
 * Created by qiqi on 17/9/19.
 */
class MyOnPageChangeListener : ViewPager.OnPageChangeListener {
    private var viewPagerTitle: ViewPagerTitle? = null
    private var viewPager: ViewPager? = null
    private var dynamicLine: DynamicLine? = null
    private var textViews: ArrayList<TextView>? = null
    private var pagerCount = 0;
    private var screenWidth = 0;
    private var lineWidth = 0
    private var everyLength = 0;//每个item的宽度
    private var dis = 0;//间距
    private var lastPosition = 0

    constructor(context: Context, viewPager: ViewPager, dynamicLine: DynamicLine, viewPagerTitle: ViewPagerTitle) {
        this.viewPagerTitle = viewPagerTitle;
        this.viewPager = viewPager
        this.dynamicLine = dynamicLine
        textViews = viewPagerTitle.getTextView()
        pagerCount = textViews!!.size
        screenWidth = getScreenWidth(context as Activity)
        lineWidth = getTextViewLength(textViews!!.get(0)).toInt()
        everyLength = screenWidth / pagerCount
        dis = (everyLength - lineWidth) / 2
    }

    fun getScreenWidth(activity: Activity): Int {
        var dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun getTextViewLength(textView: TextView): Float {
        var paint = textView.paint
        // 得到使用该paint写上text的时候,像素为多少
        return paint.measureText(textView.text.toString())
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (lastPosition > position) {
            dynamicLine!!.updateView((position + positionOffset) * everyLength + dis,
                    ((lastPosition + 1) * everyLength - dis).toFloat())
        } else {
            var pos = 0f
            if (positionOffset > 0.5f)
                pos = 0.5f
            else
                pos = positionOffset
            dynamicLine!!.updateView((lastPosition * everyLength + dis).toFloat(),
                    (position + pos * 2) * everyLength + dis + lineWidth)
        }

    }

    override fun onPageSelected(position: Int) {
        viewPagerTitle!!.setCurrentItem(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_SETTLING)
            lastPosition = viewPager!!.currentItem
    }

}