package com.example.zsq.easyshop.activity.login;

import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.model.User;
import com.example.zsq.easyshop.model.UserResult;
import com.example.zsq.easyshop.notwork.EasyShopClient;
import com.example.zsq.easyshop.notwork.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import java.io.IOException;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    public void login(String username, String password) {
        getView().showPrb();
        call = EasyShopClient.getInstance().login(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult = new Gson().fromJson(body, UserResult.class);
                if (userResult.getCode() == 1) {
                    getView().showMsg("登录成功");
                    User user = userResult.getData();
                    CachePreferences.setUser(user);
                    getView().loginSuccess();
                } else if (userResult.getCode() == 2) {
                    getView().hidePrb();
                    getView().showMsg("用户名或密码错误");
                    getView().loginFailed();
                } else {
                    getView().hidePrb();
                    getView().showMsg("未知错误！");
                }
            }
        });
    }
}
