package com.example.zsq.easyshop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.UnLoginFragment;
import com.example.zsq.easyshop.commons.ActivityUtils;
import com.example.zsq.easyshop.me.MeFragment;
import com.example.zsq.easyshop.shop.ShopFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_shop)TextView tv_shop;
    @BindView(R.id.tv_message)TextView tv_message;
    @BindView(R.id.tv_mail_list)TextView tv_mail_list;
    @BindView(R.id.tv_me)TextView tv_me;

    //声明绑定
    private Unbinder unbinder;
    //声明底部标题栏
    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    TextView[] textViews;

    //声明标题栏
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    //声明标题名
    @BindView(R.id.main_title)
    TextView tv_title;
    //声明侧滑栏
    @BindView(R.id.main_viewPager)
    ViewPager viewPager;

    //点击2次返回，退出程序
    private boolean isExit = false;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);
        //设置一下actionbar
        setSupportActionBar(toolbar);
        //设置一下ActionBar标题为空，否则默认显示应用名
        getSupportActionBar().setTitle("");

        init();
    }

    //进入页面数据初始化，默认显示为市场页面
    private void init() {
        //刚进入默认选择市场
        textViews[0].setSelected(true);
        tv_shop.setTextColor(getResources().getColor(R.color.colorAccent));
        //TODO 用户是否登录，从而选择不同的适配器
        viewPager.setAdapter(unLoginAdapter);
        viewPager.setCurrentItem(0);

        //viewpager添加滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //textView选择处理
                for (TextView textView : textViews) {
                    textView.setSelected(false);
                }
                //更改title，设置选择效果
                tv_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private FragmentStatePagerAdapter unLoginAdapter = new
            FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        //市场
                        case 0:
                            return new ShopFragment();
                        //消息
                        case 1:
                            return new UnLoginFragment();
                        //通讯录
                        case 2:
                            return new UnLoginFragment();
                        //我的
                        case 3:
                            return new MeFragment();
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return 4;
                }
            };
    //textView点击事件
    @OnClick({R.id.tv_shop,R.id.tv_message,R.id.tv_mail_list,R.id.tv_me})
    public void OnClick(TextView view){
        for(int i = 0;i<textViews.length;i++){
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //改变字体颜色
        switch ((Integer) view.getTag()){
            case 0:
                tv_shop.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_message.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_mail_list.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_me.setTextColor(getResources().getColor(R.color.text_goods_name));
                break;
            case 1:
                tv_shop.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_message.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_mail_list.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_me.setTextColor(getResources().getColor(R.color.text_goods_name));
                break;
            case 2:
                tv_shop.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_message.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_mail_list.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_me.setTextColor(getResources().getColor(R.color.text_goods_name));
                break;
            case 3:
                tv_shop.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_message.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_mail_list.setTextColor(getResources().getColor(R.color.text_goods_name));
                tv_me.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
        }

        //参数false代表瞬间切换，而不是平滑过渡
        viewPager.setCurrentItem((Integer) view.getTag(),false);
        //设置一下toolbar的title
        tv_title.setText(textViews[(Integer) view.getTag()].getText());
    }
    //点击2次返回，退出程序
    @Override
    public void onBackPressed() {
        if (!isExit){
            isExit = true;
            activityUtils.showToast("再按一次退出程序");
            //两秒内再次点击返回则退出
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },2000);
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}