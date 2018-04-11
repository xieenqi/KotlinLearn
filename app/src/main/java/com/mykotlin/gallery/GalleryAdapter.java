package com.mykotlin.gallery;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by dingguaguadabaofuwuqi on 2018/4/11.
 */

public class GalleryAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<View> mImageViewList;
    public static int sWidthPadding = DimenUtils.dp2px(40);

    public static int sHeightPadding = DimenUtils.dp2px(50);
    private View img;

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
            mImageViewList.get(position).setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);
            if (position < mImageViewList.size() - 1) {
                int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                int inWidthPadding = (int) ((1 - positionOffset) + sWidthPadding);
                mImageViewList.get(position + 1).setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
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
