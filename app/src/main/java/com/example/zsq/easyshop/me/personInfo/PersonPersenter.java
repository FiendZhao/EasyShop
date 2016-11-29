package com.example.zsq.easyshop.me.personInfo;

import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.model.User;
import com.example.zsq.easyshop.model.UserResult;
import com.example.zsq.easyshop.notwork.EasyShopApi;
import com.example.zsq.easyshop.notwork.EasyShopClient;
import com.example.zsq.easyshop.notwork.UICallBack;
import com.feicuiedu.apphx.model.HxMessageManager;
import com.feicuiedu.apphx.model.HxUserManager;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import java.io.File;
import java.io.IOException;
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
  public void updataAvatar(File file){
    getView().showPrb();
    call = EasyShopClient.getInstance().uploadAvatar(file);
    call.enqueue(new UICallBack() {
      @Override public void onFailureUI(Call call, IOException e) {
        getView().hidePrb();
        getView().showMsg("网络异常！请检查网络连接");
      }

      @Override public void onResponseUI(Call call, String body) {
        getView().hidePrb();
        UserResult userResult = new Gson().fromJson(body,UserResult.class);
        if (userResult == null){
          getView().showMsg("未知错误");
        }else if (userResult.getCode() != 1){
          getView().showMsg(userResult.getMessage());
          return;
        }

        User user = userResult.getData();
        CachePreferences.setUser(user);
        //调用activity里的头像更新方法，把url传过去
        getView().updataAvatar(userResult.getData().getHead_Image());

        //环信更新用户头像
        HxUserManager.getInstance().updateAvatar(EasyShopApi.IMAGE_URL + userResult.getData().getHead_Image());
        HxMessageManager.getInstance().sendAvatarUpdateMessage(EasyShopApi.IMAGE_URL + userResult.getData().getHead_Image());
      }
    });
  }
}
