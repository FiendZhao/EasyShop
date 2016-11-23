package com.example.zsq.easyshop.activity.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.commons.ActivityUtils;
import com.example.zsq.easyshop.commons.RegexUtils;
import com.example.zsq.easyshop.components.AlertDialogFragment;
import com.example.zsq.easyshop.components.ProgressDialogFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

/**
 * Created by ZSQ on 2016/11/16.
 * 注册页面
 */

public class ZhucheActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView{

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

    @NonNull @Override public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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
        presenter.register(username,password);

    }

    //显示错误提示
    @Override public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
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

    @Override public void registerFailed() {
        zc_et_name.setText("");
    }

    @Override public void registerSuccess() {
        //成功跳转到主页
        //TODO: 2016/11/23 0023 成功跳转到主页
        finish();
    }

    @Override public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }
}
