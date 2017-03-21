package com.mykotlin.conflict

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mykotlin.R
import kotlinx.android.synthetic.main.activity_scroll_view_silding_conflict.*
import java.util.*

/*
* 此页页面 解决 scrollView 的滑动冲突
* */
class ScrollViewSildingConflictActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_silding_conflict)
        var pagerAdapter = ViewPageAdapter(this, getPagerData())
        pager.setAdapter(pagerAdapter);
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


}
