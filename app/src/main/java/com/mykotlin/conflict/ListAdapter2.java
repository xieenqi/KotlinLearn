package com.mykotlin.conflict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mykotlin.R;

import java.util.List;

/**
 * Created by xeq on 17/3/22.
 */

public class ListAdapter2 extends BaseAdapter {
    private List<String> data;
    private LayoutInflater inflater;

    public ListAdapter2(Context mContext, List<String> data) {
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.pager_item, null);
        }
        TextView text = (TextView) view.findViewById(R.id.content);
        text.setText(data.get(i));
        return view;
    }
}
