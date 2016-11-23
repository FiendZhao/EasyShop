package com.example.zsq.easyshop.activity.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by ZSQ on 2016/11/23.
 */

public interface RegisterView extends MvpView {

  void showPrb();

  void hidePrb();

  //注册失败
  void registerFailed();
  //注册成功
  void registerSuccess();

  void showMsg(String msg);
  //用户名和密码不符合要求
  void showUserPasswordError(String msg);
}
