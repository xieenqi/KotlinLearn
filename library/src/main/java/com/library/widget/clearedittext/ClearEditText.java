package com.library.widget.clearedittext;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.library.R;

/**
 * 带清理全部文字的 按钮 的editText
 * Created by qiqi on 17/3/24.
 */

public class ClearEditText extends AppCompatEditText {

    RotateDrawable drawableRotate;
    private ValueAnimator alphaAnimator = ValueAnimator.ofInt(0, 255);
    private ValueAnimator rotateAnimator = ValueAnimator.ofInt(0, 10000);

    private ValueAnimator alphaAnimator2 = ValueAnimator.ofInt(255, 0);
    private ValueAnimator rotateAnimator2 = ValueAnimator.ofInt(10000, 0);

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setIconVisible(false, getCompoundDrawables());
    }

    private void setIconVisible(boolean b, Drawable[] drawables) {
        if (b) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().
                    getDrawable(R.drawable.edit_delete), drawables[3]);
            drawableRotate = (RotateDrawable) getCompoundDrawables()[2];
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
        }
    }

    @Override
    protected void onTextChanged(CharSequence s, int start, int before, int lengthAfter) {
        if (s.length() == 0 && before > 0) {
            //从有文字删除到无文字的时候
            startAnimatorSetResver();
            return;
        }
        if (start == 0 && s.length() > 0) {
            //从无文字到有文字
            setIconVisible(true, getCompoundDrawables());
            setAnimator();
            startAnimatorSet();
        }
    }

    private void setAnimator() {
        alphaAnimator.setDuration(1000);
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        rotateAnimator.setDuration(1000);
        rotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawableRotate.setLevel((Integer) animation.getAnimatedValue());
            }
        });
    }

    private void startAnimatorSet() {
        AnimatorSet setVisible = new AnimatorSet();
        setVisible.playTogether(alphaAnimator, rotateAnimator);
        setVisible.start();
    }

    private void startAnimatorSetResver() {
//        AnimatorSet setVisible = new AnimatorSet();
//        setVisible.playTogether(alphaAnimator2, rotateAnimator2);
//        setVisible.start();
        setIconVisible(false, getCompoundDrawables());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
                if (getCompoundDrawables()[2] != null) {
                    if (getWidth() - getTotalPaddingRight() < event.getX() &&
                            getWidth() - getPaddingRight() > event.getX()) {
                        this.setText("");
//                        Log.d("点击了图片", "图片");
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
