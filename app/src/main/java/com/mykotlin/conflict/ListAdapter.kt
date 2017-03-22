package com.mykotlin.conflict

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mykotlin.R

/**
 * Created by xeq on 17/3/22.
 */

class ListAdapter(mContext: Context, private var data: List<String>) : BaseAdapter() {
    private val inlafter: LayoutInflater

    init {
        inlafter = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getItem(p0: Int): Any? {//返回可以为 空 的任何对象
        return null
    }

    override fun getView(p0: Int, view: View?, p2: ViewGroup?): View {
        var vv = view
        if (vv == null) {
            vv = inlafter.inflate(R.layout.pager_item, null)
        }
        var text1 = vv!!.findViewById(R.id.content) as TextView
        text1.text = data!![p0]
        return vv
    }

}