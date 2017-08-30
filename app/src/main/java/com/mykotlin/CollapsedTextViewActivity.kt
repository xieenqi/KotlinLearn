package com.mykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_collapsed_text_view.*

/*主要展示  可以折叠 的 TextView */
class CollapsedTextViewActivity : AppCompatActivity() {
    val tt: String = "11112工作报告 员工每天到底做了什么？ 日报、月报、周报，随时随地及时提交 对工作结果进行点评、评估，" +
            "有有助于员工工作的自我管理 及时调整和对工作内容的细节把控 报告附件，上传各种附件、电脑、手机直接预览 " +
            "报告评论，让工作汇报变成双向的沟通 自定义工作模板，抄送人，内容等不再重复输入，工作汇报更快捷 " +
            " 自定义审批流程，快速创建流程，满足各种审批需求； 自定义审批内容，满足不同行业公司的个性化需求；" +
            " 审批节点，流程自定义步骤； 打开手机，随时提交和审批，真正实现移动办公； 审批备注，附件随意填写、" +
            "上传让审批内容更加详尽的呈现"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsed_text_view)
        text1.text = tt
        text2.setText(tt)
        text3.setText(tt)
        text4.setText(tt)
        text5.setText(tt)
    }

}
