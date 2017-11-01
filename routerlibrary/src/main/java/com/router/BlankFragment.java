package com.router;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = "/test/fragment")
public class BlankFragment extends Fragment {

    @Autowired
    String name = "bbbbbb-java-bbbbbb存储的参数-------";

//    @Autowired(required = true)
//    TestObj obj;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        return textView;
    }

}
