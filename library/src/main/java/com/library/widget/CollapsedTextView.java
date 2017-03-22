package com.library.widget;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 可以收缩 的textview 微博内容展开查看更多
 * Created by xeq on 17/3/22.
 */

public class CollapsedTextView extends AppCompatTextView {


    public CollapsedTextView(Context context) {
        super(context);
    }

    public CollapsedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        if (!TextUtils.isEmpty(getText())) {
            setText(getText());
        }
    }

    /*初始化属性*/
    private void init(Context context, AttributeSet attrs) {

    }
}
