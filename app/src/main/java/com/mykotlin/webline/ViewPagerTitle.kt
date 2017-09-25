package com.mykotlin.webline

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*
import java.util.jar.Attributes

/**
 * Created by qiqi on 17/8/30.
 */

class ViewPagerTitle : LinearLayout {
    private var titles: List<String>? = null
    private var viewPager: ViewPager? = null
    private var dynamicLine: DynamicLine? = null
    private val textViews = ArrayList<TextView>()
    private var onTextViewClick: OnTextViewClick? = null
    private var onPageChangeListener: MyOnPageChangeListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = VERTICAL
    }

    //    如果java 中的 value 参数有数组类型，则在 kotlin 中变成 vararg 参数:
    fun initData(titles: List<String>, viewPager: ViewPager, defluatIndex: Int) {
        this.titles = titles
        this.viewPager = viewPager
        createDynamicLine()
        createTextViews(titles)
        setCurrentItem(defluatIndex)
        onPageChangeListener = MyOnPageChangeListener(context, viewPager, dynamicLine!!, this)
        viewPager.addOnPageChangeListener(onPageChangeListener)
    }

    private fun createDynamicLine() {
        var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dynamicLine = DynamicLine(context)
        dynamicLine!!.layoutParams = params
    }

    private fun createTextViews(titils: List<String>) {
        var textViewLl = LinearLayout(context)
        var linearLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textViewLl.layoutParams = linearLayoutParams
        textViewLl.orientation = HORIZONTAL
        var params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        for (i in titils.indices) {
            var textView = TextView(context)
            textView.setText(titles!![i])
            textView.setTextColor(Color.GRAY)
            textView.setTextSize(18f)
            textView.layoutParams = params
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView.setOnClickListener { v ->
                for (index in textViews.indices) {
                    if (index == v.tag) {
                        (v as TextView).setTextColor(Color.BLACK)
                        (v as TextView).textSize = 20f
                    } else {
                        textViews!![index].setTextColor(Color.GRAY)
                        textViews!![index].textSize = 18f
                    }
                    viewPager!!.setCurrentItem(v.tag as Int)
                    if (onTextViewClick != null) {
                        onTextViewClick!!.textViewClick(v as TextView, v.tag as Int)
                    }
                }
            }
            textView.tag = i
            textViews.add(textView)
            textViewLl.addView(textView)
        }
        addView(textViewLl)
        addView(dynamicLine)
    }

    fun setCurrentItem(itemIndex: Int) {
        for (item in textViews.indices) {
            if (itemIndex == item) {
                textViews!![item].setTextColor(Color.BLACK)
                textViews!![item].setTextSize(20f)
            } else {
                textViews!![item].setTextColor(Color.GRAY)
                textViews!![item].setTextSize(18f)
            }
            Log.d("log", item.toString() + "====滑动的item--" + itemIndex)
        }
    }


    interface OnTextViewClick {
        fun textViewClick(textView: TextView, index: Int)
    }

    fun setOnTextViewClickListener(onTextViewClick: OnTextViewClick) {
        this.onTextViewClick = onTextViewClick
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewPager!!.removeOnPageChangeListener(onPageChangeListener)
    }

    fun getTextView(): ArrayList<TextView> {
        return textViews
    }
}