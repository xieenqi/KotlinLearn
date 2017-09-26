package com.mykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_web_line.*
import java.util.*

class WebLineActivity : AppCompatActivity() {

    private var viewsData: ArrayList<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_line)
        init()
    }

    fun init() {
//        arrayOf("layout1", "layout2", "layout3")
        var vpView: ViewPager = vp
        vpt.initData(arrayOf("头条", "军事", "人物").toList(), vpView, 0)
        viewsData = ArrayList()
        viewsData!!.add(layoutInflater.inflate(R.layout.layout1, null))
        viewsData!!.add(layoutInflater.inflate(R.layout.layout2, null))
        viewsData!!.add(layoutInflater.inflate(R.layout.layout3, null))
        val adapter = MyPagerAdapter(viewsData!!)
        vpView.adapter = adapter

    }


    class MyPagerAdapter(private val views: ArrayList<View>) : PagerAdapter() {

        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int,
                                 `object`: Any) {
            container.removeView(views[position])
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(views[position])
            return views[position]
        }
    }
}
