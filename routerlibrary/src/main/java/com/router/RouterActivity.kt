package com.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.router.R.drawable.*
import kotlinx.android.synthetic.main.activity_router.*
import android.graphics.Bitmap
import com.router.server.HelloService
import com.router.server.SingleService


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
    public var context: Activity = this

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
/*旧版本转场动画*/
        bit5.setOnClickListener {
            ARouter.getInstance()
                    .build("/test/kotlin")
                    .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                    .navigation(this)
        }
/*新版本转场动画*/
        bit6.setOnClickListener { v ->
            //http://blog.csdn.net/qibin0506/article/details/48129139
            //第1个参数是scale哪个view的大小，第2和3个参数是以view为基点，从哪开始动画，这里是该view的中心，4和5参数是新的activity从多大开始放大，这里是从无到有的过程。
            var option1 = ActivityOptionsCompat.makeScaleUpAnimation(v, v.width / 3, v.height / 3, 0, 0)
            //这里是通过放大一个图片，最后过度到一个新的activity
            var option2 = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(v, BitmapFactory.decodeResource(resources, test), 0, 0)
            var option3 = ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, getString(R.string.image))
            ARouter.getInstance()
                    .build("/test/kotlin")
                    .withOptionsCompat(option3)
                    .navigation(this)
        }
/*拦截器测试*/
        bit7.setOnClickListener {
            ARouter.getInstance()
                    .build("/test/interceptor")
                    .navigation()
            Toast.makeText(this, "请查看日志", Toast.LENGTH_SHORT).show()
        }
/*依赖注入(参照代码)*/
        bit8.setOnClickListener {
            //传递各种参数
            ARouter.getInstance()
                    .build("/test/kotlin")
                    .withParcelable("pac", TestParcelable())
                    .navigation()
        }
/*ByName调用服务 */
        bit9.setOnClickListener { (ARouter.getInstance().build("/service/hello").navigation() as HelloService).sayHello("mike 调用服务") }
/*ByType调用服务*/
        bit10.setOnClickListener { ARouter.getInstance().navigation(HelloService::class.java).sayHello("mike --- ByType调用服务") }
/*ARouter 调用单类*/
        bit11.setOnClickListener { ARouter.getInstance().navigation<SingleService>(SingleService::class.java).sayHello("Mike--调用单类") }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            EXIT_CODE -> Toast.makeText(application, "返回值返回成功", Toast.LENGTH_LONG)
        }
    }

    companion object {
        fun getActivity() {

        }
    }
}
