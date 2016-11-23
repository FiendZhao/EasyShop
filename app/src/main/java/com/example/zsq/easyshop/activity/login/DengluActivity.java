package com.example.zsq.easyshop.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.activity.MainActivity;
import com.example.zsq.easyshop.activity.register.ZhucheActivity;
import com.example.zsq.easyshop.commons.ActivityUtils;
import com.example.zsq.easyshop.components.ProgressDialogFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

/**
 * Created by ZSQ on 2016/11/16.
 * 登录界面
 */

public class DengluActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{
    //声明标题栏
    @BindView(R.id.toolbar)Toolbar toolbar;
    //声明快速注册按钮
    @BindView(R.id.dl_tv_kszc)TextView dl_tv_kszc;
    //声明用户名
    @BindView(R.id.dl_et_name)EditText dl_ed_name;
    //声明密码
    @BindView(R.id.dl_et_mima)EditText dl_ed_mima;
    //声明登录按钮
    @BindView(R.id.dl_btn)Button dl_btn;

    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu_activity);
        //绑定黄油刀
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        init();
    }

    @NonNull @Override public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private void init(){
        dl_ed_name.addTextChangedListener(textWatcher);
        dl_ed_mima.addTextChangedListener(textWatcher);
        if (dialogFragment == null)
            dialogFragment = new ProgressDialogFragment();
        setSupportActionBar(toolbar);
        //给左上角添加一个返回图标，需要重写菜单点击事件，否则点击无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //输入设置
    private TextWatcher textWatcher = new TextWatcher() {
        //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。
        //而after表示改变后新的内容的数量。
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        //这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。
        //而before表示被改变的内容的数量。
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        //表示最终内容
        @Override
        public void afterTextChanged(Editable s) {
            username = dl_ed_name.getText().toString();
            password = dl_ed_mima.getText().toString();
            //判断用户名和密码是否为空
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
            dl_btn.setEnabled(canLogin);
        }
    };
    //点击事件
    @OnClick({R.id.dl_tv_kszc,R.id.dl_btn})
    public void OnClick(View view){
        switch (view.getId()){
            //跳转到注册页面
            case R.id.dl_tv_kszc:
                startActivity(new Intent(this,ZhucheActivity.class));
                break;
            //关闭页面返回
            case R.id.dl_btn:
                presenter.login(username,password);
                break;
        }

    }

    @Override public void showPrb() {
        activityUtils.hideSoftKeyboard();
        if (dialogFragment==null) dialogFragment = new ProgressDialogFragment();
        if (dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }

    @Override public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override public void loginFailed() {
        dl_ed_name.setText("");
    }

    @Override public void loginSuccess() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }
}
