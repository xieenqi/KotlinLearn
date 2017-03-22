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

class ListAdapter3(mContext: Context, private val data: List<String>?) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
//        如果 ?: 左边表达式非空，elvis操作符就会返回左边的结果，否则返回右边的结果。
//        请注意，仅在左侧为空的时候，右侧的表达式才会计算
        return data?.size ?: 0
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var vv = view
        if (vv == null) {
            vv = inflater.inflate(R.layout.pager_item, null)
        }
        /* as 类型转换  as? 安全转换 转换不成功返回null   不会抛出异常 */
        val textt = vv!!.findViewById(R.id.content) as TextView
        textt.text = data!![i]
        return vv
    }
}
