package com.example.zsq.easyshop.me.personInfo;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import okhttp3.Call;

/**
 * Created by ZSQ on 2016/11/23.
 */

public class PersonPersenter extends MvpNullObjectBasePresenter<PersonView>{
  private Call call;

  @Override public void detachView(boolean retainInstance) {
    super.detachView(retainInstance);
    if (call!=null) call.cancel();
  }
  //上传头像
  public void updataAvatar(){

  }
}
