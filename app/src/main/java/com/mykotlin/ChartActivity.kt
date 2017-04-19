package com.mykotlin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.library.widget.chart.LineChartView
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*

/*图标控件页面*/
class ChartActivity : AppCompatActivity() {

     var  mItems: ArrayList<LineChartView.ItemBean>? = null;

     var  shadeColors: IntArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        //  初始化折线数据
        mItems = ArrayList()
        mItems!!.add(LineChartView.ItemBean(1489507200, 0))
        mItems!!.add(LineChartView.ItemBean(1489593600, 88))
        mItems!!.add(LineChartView.ItemBean(1489680000, 60))
        mItems!!.add(LineChartView.ItemBean(1489766400, 50))
        mItems!!.add(LineChartView.ItemBean(1489852800, 70))
        mItems!!.add(LineChartView.ItemBean(1489939200, 10))
        mItems!!.add(LineChartView.ItemBean(1490025600, 33))
        mItems!!.add(LineChartView.ItemBean(1490112000, 44))
        mItems!!.add(LineChartView.ItemBean(1490198400, 99))
        mItems!!.add(LineChartView.ItemBean(1490284800, 17))

        shadeColors = intArrayOf(Color.argb(100, 255, 86, 86), Color.argb(15, 255, 86, 86), Color.argb(0, 255, 86, 86))
        lcv!!.items=mItems
        lcv!!.shadeColors=shadeColors
        lcv.startAnim(lcv,1000)
    }
}
