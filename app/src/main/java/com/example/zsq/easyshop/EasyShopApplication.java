package com.example.zsq.easyshop;

import android.app.Application;

import com.example.zsq.easyshop.model.CachePreferences;

/**
 * Created by ZSQ on 2016/11/21.
 */

public class EasyShopApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化本地配置
        CachePreferences.init(this);
    }
}
