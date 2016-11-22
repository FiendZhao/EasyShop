package com.example.zsq.easyshop.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.activity.DengluActivity;
import com.example.zsq.easyshop.commons.ActivityUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    private View view;
    private ActivityUtils activityUtils;
    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_me,container,false);
            ButterKnife.bind(this,view);
        }
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // TODO: 2016/11/16 判断用户是否登录，更改用户头像并且显示用户名
    }

    @OnClick({R.id.me_civ_touxiang, R.id.me_tv_denglu})
    public void onClick() {
        // TODO: 2016/11/16 判断用户是否登录，来确定跳转位置
        activityUtils.startActivity(DengluActivity.class);
    }
}
