package com.example.zsq.easyshop.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.activity.login.DengluActivity;
import com.example.zsq.easyshop.commons.ActivityUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zsq.easyshop.components.AvatarLoadOptions;
import com.example.zsq.easyshop.me.personInfo.PersonActivity;
import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.notwork.EasyShopApi;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    @BindView(R.id.me_civ_touxiang) ImageView me_civ_touxiang;
    @BindView(R.id.me_tv_denglu) TextView me_tv_denglu;
    private View view;
    private ActivityUtils activityUtils;

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
        if (CachePreferences.getUser().getName()==null)return;
        if (CachePreferences.getUser().getNick_name()==null){
            me_tv_denglu.setText("请输入昵称");
        }else {
            me_tv_denglu.setText(CachePreferences.getUser().getNick_name());
        }
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + CachePreferences.getUser().getHead_Image()
            ,me_civ_touxiang, AvatarLoadOptions.build());
    }

    @OnClick({R.id.me_civ_touxiang,R.id.me_tv_denglu,R.id.tv_person_info,R.id.tv_person_goods,R.id.tv_goods_upload})
    public void onClick(View view) {
        if (CachePreferences.getUser().getName()==null){
            activityUtils.startActivity(DengluActivity.class);
            return;
        }
        switch (view.getId()){
            case R.id.me_civ_touxiang:
            case R.id.me_tv_denglu:
            case R.id.tv_person_info:
                activityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods:
                activityUtils.showToast("我的商品 待实现");
                break;
            case R.id.tv_goods_upload:
                activityUtils.showToast("商品上传 待实现");
                break;
        }
    }
}
