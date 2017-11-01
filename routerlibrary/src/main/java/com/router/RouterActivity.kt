package com.router

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_router.*

//https://github.com/alibaba/ARouter  阿里巴巴开源

@Route(path = "/router/kotlin")
class RouterActivity : AppCompatActivity() {
    // 为每一个传递参数声明一个字段，并使用 @Autowired 标注
    //kotlin是属性默认是private不能直接访问，注解@JvmField编译过后就是public属性
    //@JvmField 注解无法用于 private 类属性，没有必要；
    @Autowired(name = "parma")//name 是传递的key，默认是参数名作为key
    @JvmField
    var parma1 = ""
    @Autowired
    @JvmField
    var parma2 = 0

    val EXIT_CODE = 10086

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_router)
        bit1.setOnClickListener {
            ARouter.getInstance().build("/test/kotlin").navigation()
        }
        //不使用自动注入 需要自己去取数据
//        text1.text="传递过来的测试参数："+intent.getStringExtra("parma1")
        //自动注入功能
        ARouter.getInstance().inject(this);
        text1.text = "传递过来的测试参数：" + parma1
        bit2.setOnClickListener {
            ARouter.getInstance()
                    .build("/test/kotlin")
                    .withString("textInfo", "-----测试传递的文字信息-----").navigation()
        }
        bit3.setOnClickListener {
            ARouter.getInstance()
                    .build("/test/kotlin")
                    .navigation(this, EXIT_CODE)
//            startActivityForResult(Intent(this, RouterTestActivity::class.java), EXIT_CODE)
//TODO kotlin中不能回调参数
        }
        bit4.setOnClickListener {
            //            var fragment = (ARouter.getInstance().build("/test/kotlin/fragment").navigation()) as TestFragment
            var fragment = (ARouter.getInstance().build("/test/fragment").navigation()) as BlankFragment
            Toast.makeText(this, "找到Fragment--:" + fragment.name, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            EXIT_CODE -> Toast.makeText(application, "返回值返回成功", Toast.LENGTH_LONG)
        }
    }
}
