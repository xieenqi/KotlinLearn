package com.mykotlin

import android.content.Context
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_input_show_hide.*
import android.view.ViewTreeObserver
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import android.widget.Toast


/*  软键盘的 弹出出于隐藏 */

class InputShowHideActivity : AppCompatActivity() {
    // 状态栏的高度
    private var statusBarHeight: Int = 0
    // 软键盘的高度
    private var keyboardHeight: Int = 0
    // 软键盘的显示状态
    private var isShowKeyboard: Boolean = false
    private val inputDistance = 320f//输入框位移值
    private val durationMillis: Long = 500//动画运行时间
    var logoAnim1: Animation? = null
    var logoAnim2: Animation? = null
    var loginInputAnim1: Animation? = null
    var loginInputAnim2: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 记住：这句代码要写在setContentView()前面。
        setContentView(R.layout.activity_input_show_hide)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarHeight = getStatusBarHeight(getApplicationContext());
        root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        initAnim()
    }

    var globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        // 应用可以显示的区域。此处包括应用占用的区域，
        // 以及ActionBar和状态栏，但不含设备底部的虚拟按键。
        var r = Rect()
        root.getWindowVisibleDisplayFrame(r)
        // 屏幕高度。这个高度不含虚拟按键的高度
        var screenHeight = root.rootView.height
        var b = r.bottom
        var t = r.top
        var heightDiff = screenHeight - (b - t)
        // 在不显示软键盘时，heightDiff等于状态栏的高度
        // 在显示软键盘时，heightDiff会变大，等于软键盘加状态栏的高度。
        // 所以heightDiff大于状态栏高度时表示软键盘出现了，
        // 这时可算出软键盘的高度，即heightDiff减去状态栏的高度
        if (keyboardHeight == 0 && heightDiff > statusBarHeight) {
            keyboardHeight = heightDiff - statusBarHeight
        }
        if (isShowKeyboard) {
            // 如果软键盘是弹出的状态，并且heightDiff小于等于状态栏高度，
            // 说明这时软键盘已经收起
            if (heightDiff <= statusBarHeight) {
                isShowKeyboard = false;
                onHideKeyboard()
            }
        } else {
            // 如果软键盘是收起的状态，并且heightDiff大于状态栏高度，
            // 说明这时软键盘已经弹出
            if (heightDiff > statusBarHeight) {
                isShowKeyboard = true
                onShowKeyBoard()
            }
        }
    }

    /* 在这里处理软键盘收回的回调*/
    fun onHideKeyboard() {
        Toast.makeText(this, "yyy影藏截屏", 0).show()
        logo.startAnimation(logoAnim2)
        inputLayout.startAnimation(loginInputAnim2)
    }

    /* 在这里处理软键盘弹出的回调*/
    fun onShowKeyBoard() {
        Toast.makeText(this, "键盘弹出", 0).show()
        logo.startAnimation(logoAnim1)
        inputLayout.startAnimation(loginInputAnim1)
    }

    // 获取状态栏高度
    fun getStatusBarHeight(applicationContext: Context?): Int {
        val c = Class.forName("com.android.internal.R\$dimen")
        var obj = c.newInstance()
        var field = c.getField("status_bar_height")
        var x: Int = field.get(obj).toString().toInt()
        return applicationContext!!.resources.getDimensionPixelSize(x)
    }


    /*动画初始化*/
    fun initAnim() {
        logoAnim1 = AnimationUtils.loadAnimation(this, R.anim.login_logo1)
        logoAnim2 = AnimationUtils.loadAnimation(this, R.anim.login_logo2)
        loginInputAnim1 = TranslateAnimation(0f, 0f, 0f, -inputDistance)
        (loginInputAnim1 as TranslateAnimation).duration = durationMillis
        (loginInputAnim1 as TranslateAnimation).fillAfter = true //动画到某处不返回原处
        (loginInputAnim1 as TranslateAnimation).setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                //清除动画
                inputLayout.clearAnimation()
                val params = inputLayout.layoutParams as RelativeLayout.LayoutParams
                params.topMargin = params.topMargin - inputDistance.toInt()
                inputLayout.layoutParams = params
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        loginInputAnim2 = TranslateAnimation(0f, 0f, 0f, inputDistance)
        (loginInputAnim2 as TranslateAnimation).duration = durationMillis
        (loginInputAnim2 as TranslateAnimation).fillAfter = true
        (loginInputAnim2 as TranslateAnimation).setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                //清除动画
                inputLayout.clearAnimation()
                var params = inputLayout.layoutParams as RelativeLayout.LayoutParams
                params.topMargin = params.topMargin + inputDistance.toInt()
                inputLayout.layoutParams = params
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
}
