package com.mykotlin.webline

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.widget.Scroller
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_scroll_view_silding_conflict.view.*

/**
 * Created by qiqi on 17/9/6.
 */

class DynamicLine : View {
    private var paint: Paint? = null
    private var scroller: Scroller? = null
    private var startX: Float = 0.toFloat()
    private var stopX: Float = 0.toFloat()
    private val rectF = RectF(startX, 0f, stopX, 0f)
    private var location1: Int = 0
    private var location2: Int = 0
    private var location3: Int = 0
    private var sportL: Int = 0
    private var defaultScrooll: Boolean = false

    constructor(context: Context) : super(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.FILL
        paint!!.strokeWidth = 5f
        paint!!.shader = LinearGradient(0f, 100f, getScreenWidth(context as Activity).toFloat(), 100f,
                Color.parseColor("#ffc125"), Color.parseColor("#ff4500"), Shader.TileMode.MIRROR)
        scroller = Scroller(context)
    }

    private fun getScreenWidth(activity: Activity): Int {
        var dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20, MeasureSpec.getMode(heightMeasureSpec))
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        rectF.set(startX, 0f, stopX, 10f)
        canvas!!.drawRoundRect(rectF, 5f, 5f, paint)
        super.onDraw(canvas)
    }

    fun updateView(startX: Float, stopX: Float) {
        this.startX = startX
        this.stopX = stopX
        invalidate()
    }

    fun setLocation(loacation1: Int, loacation2: Int, loacation3: Int, defaultScrooll: Boolean) {
        this.sportL = 0
        this.location1 = location1
        this.location2 = location2
        this.location3 = location3
        this.defaultScrooll = defaultScrooll;
        if (defaultScrooll) {
            stopX = loacation3.toFloat()
            scroller!!.startScroll(loacation1, 0, loacation2 - loacation1, 0, 2000)
        } else {
            startX = loacation1.toFloat()
            scroller!!.startScroll(loacation2, 0, loacation3 - loacation2, 0, 2000)
        }
        invalidate()//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    override fun computeScroll() {
        super.computeScroll()
        //先判断mScroller滚动是否完成
        if (scroller!!.computeScrollOffset()) {
            if (defaultScrooll) {
                startX = scroller!!.currX.toFloat()
            } else {
                sportL = scroller!!.currX - location2
                stopX = (location3 - sportL).toFloat()
            }
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate()
        }
    }

    fun getTextViewLength(textView: TextView, text: String): Float {
        val paint = textView.paint
        // 得到使用该paint写上text的时候,像素为多少
        return paint.measureText(text)
    }
}