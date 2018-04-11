package com.mykotlin.gallery;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mykotlin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/*画廊的实现*/
public class GalleryActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.root)
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        List<View> mImageViewList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
//            ImageView img = new ImageView(this);
//            img.setBackgroundColor(Color.parseColor(getRandColorCode()));
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.zwy_edoc_fore));

            View galleryView = layoutInflater.inflate(R.layout.item_gallery, null, false);
            galleryView.setBackgroundColor(Color.parseColor(getRandColorCode()));
            mImageViewList.add(galleryView);
        }
        GalleryAdapter adapter = new GalleryAdapter(mImageViewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(adapter);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return viewPager.dispatchTouchEvent(motionEvent);
            }
        });
    }

    /**
     * 获取随机颜色，便于区分
     *
     * @return
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#" + r + g + b;
    }


}
