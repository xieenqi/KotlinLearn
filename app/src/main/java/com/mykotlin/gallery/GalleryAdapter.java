package com.mykotlin.gallery;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.common.Logger;
import com.mykotlin.R;

import java.util.List;

/**
 * Created by dingguaguadabaofuwuqi on 2018/4/11.
 */

public class GalleryAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<View> mImageViewList;
    public static int sWidthPadding = DimenUtils.dp2px(24);

    public static int sHeightPadding = DimenUtils.dp2px(42);
    public static int margenFM = DimenUtils.dp2px(25);

    public GalleryAdapter(List<View> mImageViewList) {
        this.mImageViewList = mImageViewList;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View img = mImageViewList.get(position);
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewList.get(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mImageViewList.size() > 0 && position < mImageViewList.size()) {
            int outHeightPadding = (int) (positionOffset * sHeightPadding);
            int outWidthPadding = (int) (positionOffset * sWidthPadding);
            View itemView = mImageViewList.get(position);
            ImageView img_bg = itemView.findViewById(R.id.img_bg);
            ImageView img_fm = itemView.findViewById(R.id.img_fm);
            if (outWidthPadding != 0 && outHeightPadding != 0) {
                img_fm.setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);
                img_bg.setPadding(outWidthPadding, (outHeightPadding + outHeightPadding * 3 / 5), outWidthPadding, (outHeightPadding + outHeightPadding * 2 / 5));
//                Log.d("applog", outHeightPadding + " 滚动宽度：==== " + outWidthPadding);
            }
            if (position < mImageViewList.size() - 2) {
                View itemView3 = mImageViewList.get(position + 2);
                ImageView img_bg3 = itemView3.findViewById(R.id.img_bg);
                ImageView img_fm3 = itemView3.findViewById(R.id.img_fm);
                if (outWidthPadding != 0 && outHeightPadding != 0) {
                    img_fm3.setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);
                    img_bg3.setPadding(outWidthPadding, (outHeightPadding + outHeightPadding * 3 / 5), outWidthPadding, (outHeightPadding + outHeightPadding * 2 / 5));
                }
            }
            Log.d("applog", "当前：" + position);
            if (position < mImageViewList.size() - 1) {
                int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                int inWidthPadding = (int) ((1 - positionOffset) + sWidthPadding);
                View itemView2 = mImageViewList.get(position + 1);
                ImageView img_bg2 = itemView2.findViewById(R.id.img_bg);
                ImageView img_fm2 = itemView2.findViewById(R.id.img_fm);

                if (inWidthPadding != 0 && inHeightPadding != 0) {
                    img_fm2.setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
                    img_bg2.setPadding(inWidthPadding, (inHeightPadding + inHeightPadding * 3 / 5), inWidthPadding, (inHeightPadding + inHeightPadding * 3 / 5));
                    Log.d("applog", inHeightPadding + " 滚动宽度：---- " + inWidthPadding);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
