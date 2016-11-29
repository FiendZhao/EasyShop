package com.example.zsq.easyshop.activity.register;

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
 * Created by ZSQ on 2016/11/23.
 */

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{

  private Call call;
  //因为环信需要密码
  private String hxPassword;

  @Override
  public void attachView(RegisterView view) {
    super.attachView(view);
    EventBus.getDefault().register(this);
  }

  //视图销毁，取消网络请求
  @Override public void detachView(boolean retainInstance) {
    super.detachView(retainInstance);
    if (call!=null)call.cancel();
    EventBus.getDefault().unregister(this);
  }

  public void register(String username,String password){
    hxPassword = password;
    //显示加载动画
    getView().showPrb();
    call = EasyShopClient.getInstance().register(username,password);
    call.enqueue(new UICallBack() {
      @Override
      public void onFailureUI(Call call, IOException e) {
        hxPassword = null;
        //隐藏动画
        getView().hidePrb();
        //显示异常信息
        getView().showMsg("网络异常！请检查网络连接");
      }

      @Override
      public void onResponseUI(Call call, String body) {
        //拿到返回的结果
        UserResult userResult = new Gson().fromJson(body,UserResult.class);
        //根据结果码处理不同情况
        if (userResult.getCode()==1){
          //成功提示
          getView().showMsg("注册成功！");
          //拿到用户的实体类
          User user = userResult.getData();
          //将用户信息保存到本地配置里
          CachePreferences.setUser(user);

          EaseUser easeUser = CurrentUser.convert(user);
          HxUserManager.getInstance().asyncLogin(easeUser,hxPassword);
        }else if (userResult.getCode()==2){
          //隐藏进度条
          getView().hidePrb();
          //提示错误信息
          getView().showMsg(userResult.getMessage());
          //调用注册失败的方法
          getView().registerFailed();
        }else {
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
    //调用注册成功的方法
    getView().registerSuccess();
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
