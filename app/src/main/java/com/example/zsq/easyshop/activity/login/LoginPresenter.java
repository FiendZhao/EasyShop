package com.example.zsq.easyshop.activity.login;

import com.example.zsq.easyshop.commons.CurrentUser;
import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.model.User;
import com.example.zsq.easyshop.model.UserResult;
import com.example.zsq.easyshop.notwork.EasyShopClient;
import com.example.zsq.easyshop.notwork.UICallBack;
import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;
import java.io.IOException;
import okhttp3.Call;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call call;
    private String hxPassword;

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
        EventBus.getDefault().unregister(this);
    }

    public void login(String username, String password) {
        hxPassword = password;
        getView().showPrb();
        call = EasyShopClient.getInstance().login(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                hxPassword = null;
                getView().hidePrb();
                getView().showMsg("网络异常！请检查网络连接");
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult = new Gson().fromJson(body, UserResult.class);
                if (userResult.getCode() == 1) {
                    User user = userResult.getData();
                    CachePreferences.setUser(user);

                    EaseUser easeUser = CurrentUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser,hxPassword);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //判断是否是登录成功事件
        if (event.type != HxEventType.LOGIN) return;

        hxPassword = null;
        //调用登录成功的方法
        getView().loginSuccess();
        getView().showMsg("登录成功");

        EventBus.getDefault().post(new UserResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        //判断是否是登录成功事件
        if (event.type != HxEventType.LOGIN) return;

        hxPassword = null;
        getView().hidePrb();
        getView().showMsg(event.toString());
    }
}
