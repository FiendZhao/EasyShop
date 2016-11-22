package com.example.zsq.easyshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.zsq.easyshop.R;

/**
 * Created by ZSQ on 2016/11/16.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        //TODO 登录帐号冲突
        //TODO 判断用户是否登录
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));

                //跳转之后关闭此界面
                finish();
                //设置Activity的跳转动画
                overridePendingTransition(R.anim.activity_enter,R.anim.ativity_exit);
                return false;
            }
        }).sendEmptyMessageDelayed(0,1000);
    }
}