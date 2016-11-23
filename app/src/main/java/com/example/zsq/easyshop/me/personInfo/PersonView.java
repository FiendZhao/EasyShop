package com.example.zsq.easyshop.me.personInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by ZSQ on 2016/11/23.
 */

public interface PersonView extends MvpView{
  void showPrb();

  void hidePrb();

  void showMsg(String msg);

  //用来更新头像
  void updataAvatar(String url);
}
