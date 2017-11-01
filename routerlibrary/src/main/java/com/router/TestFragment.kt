package com.router

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route

/** 测试 不支持kotlin fragment实例
 * Created by qiqi on 17/11/1.
 */
@Route(path = "/test/kotlin/fragment")
class TestFragment : Fragment() {

    @Autowired
    @JvmField
    var name = "bbbbbb-kotlin-bbbbbb存储的参数-------"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        return textView
    }
}