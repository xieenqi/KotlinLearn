package com.mykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/*
* 此页页面 解决 scrollView 的滑动冲突
* */
class ScrollViewSildingConflictActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view_silding_conflict)
    }
}
