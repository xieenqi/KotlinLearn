package com.mykotlin.conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * 检查 scrollview是否滚动到 底部  还打  顶部
 * Created by xeq on 17/3/22.
 */

public class ScrollBottomView extends ScrollView {

    private onScrollListener listener;

    public ScrollBottomView(Context context) {
        super(context);
    }

    public ScrollBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*复写了view里面的方法
    * onScrollChanged里面有4个参数，l代表滑动后当前ScrollView可视界面的左上角在整个ScrollView的X轴中的位置，oldi也就是滑动前的X轴位置了。
同理，t也是当前可视界面的左上角在整个ScrollView的Y轴上的位置，oldt也就是移动前的Y轴位置了。
    * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.d("log", "l ->" + l + "  t -> " + t + " oldl -> " + oldl + " oldt->  " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            //滑动的距离加上本身的高度 与 子view的高度
            if (t + getHeight() >= getChildAt(0).getMeasuredHeight()) {
                listener.onScrollBottom();
            } else if (t <= 0) {
                listener.onScrollTop();
            } else {
                listener.onScrollMiddle();
            }
        }
    }

    public void setListener(onScrollListener listener) {
        this.listener = listener;
    }

    public interface onScrollListener {
        /*滚动到 顶部*/
        void onScrollTop();

        /*滚动 中*/
        void onScrollMiddle();

        /*滚动到 底部*/
        void onScrollBottom();
    }
}
