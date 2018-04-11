package com.mykotlin.gallery;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mykotlin.R;

import java.util.List;

/**
 * Created by dingguaguadabaofuwuqi on 2018/4/11.
 */

public class GalleryAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<View> mImageViewList;
    public static int sWidthPadding = DimenUtils.dp2px(24);

    public static int sHeightPadding = DimenUtils.dp2px(32);

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
//            img_bg.setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);
            img_fm.setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding);

            if (position < mImageViewList.size() - 1) {
                int inHeightPadding = (int) ((1 - positionOffset) * sHeightPadding);
                int inWidthPadding = (int) ((1 - positionOffset) + sWidthPadding);
                View itemView2 = mImageViewList.get(position + 1);
                ImageView img_bg2 = itemView2.findViewById(R.id.img_bg);
                ImageView img_fm2 = itemView2.findViewById(R.id.img_fm);
//                img_bg2.setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
                img_fm2.setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding);
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
