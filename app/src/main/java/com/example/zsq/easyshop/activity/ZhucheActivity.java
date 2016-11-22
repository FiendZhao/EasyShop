package com.example.zsq.easyshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.commons.ActivityUtils;
import com.example.zsq.easyshop.commons.RegexUtils;
import com.example.zsq.easyshop.components.AlertDialogFragment;
import com.example.zsq.easyshop.components.ProgressDialogFragment;
import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.model.User;
import com.example.zsq.easyshop.model.UserResult;
import com.example.zsq.easyshop.notwork.EasyShopClient;
import com.example.zsq.easyshop.notwork.UICallBack;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by ZSQ on 2016/11/16.
 * 注册页面
 */

public class ZhucheActivity extends AppCompatActivity{

    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;
    //用户输入的用户名密码与确认密码
    private String username;
    private String password;
    private String pwd_again;

    //声明标题栏
    @BindView(R.id.toolbar)Toolbar toolbar;
    //声明用户名
    @BindView(R.id.zc_et_name)EditText zc_et_name;
    //声明密码
    @BindView(R.id.zc_et_mima)EditText zc_et_mima;
    //声明确认密码
    @BindView(R.id.zc_et_qrmima)EditText zc_et_qrmima;
    //声明注册按钮
    @BindView(R.id.zc_btn)Button zc_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuche_activity);
        //绑定黄油刀
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        init();
    }

    private void init(){
        zc_et_name.addTextChangedListener(textWatcher);
        zc_et_mima.addTextChangedListener(textWatcher);
        zc_et_qrmima.addTextChangedListener(textWatcher);
        setSupportActionBar(toolbar);
        //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //输入设置
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            username = zc_et_name.getText().toString();
            password = zc_et_mima.getText().toString();
            pwd_again = zc_et_qrmima.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username)||TextUtils
            .isEmpty(password)||TextUtils.isEmpty(pwd_again));
            zc_btn.setEnabled(canLogin);
        }
    };

    //点击事件
    @OnClick(R.id.zc_btn)
    public void OnClick(){
        if (RegexUtils.verifyUsername(username)!=RegexUtils.VERIFY_SUCCESS){
            String msg = getString(R.string.username_rules);
            showUserPasswordError(msg);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.password_rules);
            showUserPasswordError(msg);
            return;
        } else if (!TextUtils.equals(password, pwd_again)) {
            String msg = getString(R.string.username_equal_pwd);
            showUserPasswordError(msg);
            return;
        }
        Call call = EasyShopClient.getInstance().register(username,password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                activityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                //拿到返回的结果
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                //根据结果码处理不同情况
                if (userResult.getCode()==1){

                    activityUtils.showToast("注册成功！");
                    //拿到用户的实体类
                    User user = userResult.getData();
                    //将用户信息保存到本地配置里
                    CachePreferences.setUser(user);

                    //TODO:页面跳转实现，使用eventbus
                    //TODO:需要登录环信，待实现

                }else if (userResult.getCode()==2){
                    activityUtils.showToast(userResult.getMessage());
                }else {
                    activityUtils.showToast("未知错误！");
                }
            }
        });

    }


    //显示错误提示
    public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
    }

}
