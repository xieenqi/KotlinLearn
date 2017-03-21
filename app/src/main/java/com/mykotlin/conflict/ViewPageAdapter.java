package com.mykotlin.conflict;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mykotlin.R;

import java.util.List;

/**
 * Created by xeq on 17/3/21.
 */

public class ViewPageAdapter extends PagerAdapter {
    private List<String> data;
    private Context mContext;
    private LayoutInflater inflater;

    public ViewPageAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.pager_item, null);
        TextView text = (TextView) view.findViewById(R.id.content);
        if (data != null)
            text.setText(data.get(position));
        container.addView(view);
        return view;
    }
}
