package com.example.zsq.easyshop.me.personInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.activity.MainActivity;
import com.example.zsq.easyshop.commons.ActivityUtils;
import com.example.zsq.easyshop.components.AvatarLoadOptions;
import com.example.zsq.easyshop.components.PicWindow;
import com.example.zsq.easyshop.components.ProgressDialogFragment;
import com.example.zsq.easyshop.model.CachePreferences;
import com.example.zsq.easyshop.model.ItemShow;
import com.example.zsq.easyshop.model.User;
import com.example.zsq.easyshop.notwork.EasyShopApi;
import com.feicuiedu.apphx.model.HxUserManager;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

/**
 * Created by ZSQ on 2016/11/23.
 */

public class PersonActivity extends MvpActivity<PersonView,PersonPersenter> implements PersonView{

  @BindView(R.id.toolbar)Toolbar toolbar;
  @BindView(R.id.iv_user_head) ImageView ivUserHead;
  @BindView(R.id.listView)ListView listView;

  private ActivityUtils activityUtils;
  private ProgressDialogFragment progressDialogFragment;
  private List<ItemShow> list = new ArrayList<>();
  private PersonAdapter adapter;
  private PicWindow picWindow;
  //权限
  private String[] quanxianSZ = {"android.permission.WRITE_EXTERNAL_STORAGE"};
  private String quanxian = "android.permission.WRITE_EXTERNAL_STORAGE";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person);
    ButterKnife.bind(this);

    activityUtils = new ActivityUtils(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    adapter = new PersonAdapter(list);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(onItemClickListener);

    //获取用户头像
    updataAvatar(CachePreferences.getUser().getHead_Image());
  }

  //方便修改完昵称，回来更改数据
  @Override protected void onStart() {
    super.onStart();
    list.clear();
    init();
    adapter.notifyDataSetChanged();
  }

  private void init(){
    User user = CachePreferences.getUser();
    list.add(new ItemShow(getResources().getString(R.string.username),user.getName()));
    list.add(new ItemShow(getResources().getString(R.string.nickname),user.getNick_name()));
    list.add(new ItemShow(getResources().getString(R.string.hx_id),user.getHx_Id()));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) finish();
    return super.onOptionsItemSelected(item);
  }
  private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
    @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      switch (i){
        case 0:
          activityUtils.showToast(getResources().getString(R.string.username_update));
          break;
        case 1:
          activityUtils.startActivity(NickNameActivity.class);
          break;
        case 2:
          activityUtils.showToast(getResources().getString(R.string.id_update));
          break;
      }
    }
  };

  @NonNull @Override public PersonPersenter createPresenter() {
    return new PersonPersenter();
  }

  @Override public void showPrb() {
    if (progressDialogFragment == null) progressDialogFragment = new ProgressDialogFragment();
    if (progressDialogFragment.isVisible()) return;
    progressDialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
  }

  @Override public void hidePrb() {
    progressDialogFragment.dismiss();
  }

  @Override public void showMsg(String msg) {
    activityUtils.showToast(msg);
  }

  @Override public void updataAvatar(String url) {
    ImageLoader.getInstance()
        .displayImage(EasyShopApi.IMAGE_URL + url,ivUserHead, AvatarLoadOptions.build());
  }

  @OnClick({R.id.iv_user_head,R.id.btn_login_out})
  public void onClick(View view){
    switch (view.getId()){
      case R.id.iv_user_head:
        //添加动态权限
        if (ActivityCompat.checkSelfPermission(this, quanxian) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,quanxianSZ,1);
      }
        if (picWindow == null){
          //图片选择弹窗的自定义监听
          picWindow = new PicWindow(this,listener);
        }
        if (picWindow.isShowing()){
          picWindow.dismiss();
          return;
        }
        picWindow.show();
        break;
      case R.id.btn_login_out:
        //环信的退出登录
        HxUserManager.getInstance().asyncLogout();
        //登出关掉通知栏中的通知
        EaseUI.getInstance().getNotifier().reset();
        //清空本地配置
        CachePreferences.clearAllData();
        Intent intent = new Intent(this, MainActivity.class);
        //清除所有旧的activtiy
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
  }
  private PicWindow.Listener listener = new PicWindow.Listener() {
    @Override public void toGallery() {
      //从相册中选择
      //清空裁剪的缓存
      CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
      Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
      startActivityForResult(intent,CropHelper.REQUEST_CROP);

    }

    @Override public void toCamera() {
      //从相机中选择
      CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
      Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
      startActivityForResult(intent,CropHelper.REQUEST_CAMERA);
    }
  };

  //图片剪切
  private CropHandler cropHandler = new CropHandler() {
    @Override public void onPhotoCropped(Uri uri) {
      //通过uri拿到图片文件
      File file =  new File(uri.getPath());
      //业务类上传头像
      presenter.updataAvatar(file);

    }

    @Override public void onCropCancel() {

    }

    @Override public void onCropFailed(String message) {

    }

    @Override public CropParams getCropParams() {
      //自定义裁剪大小参数
      CropParams cropParams = new CropParams();
      cropParams.aspectX = 400;
      cropParams.aspectY = 400;
      return cropParams;
    }

    @Override public Activity getContext() {
      return PersonActivity.this;
    }
  };

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //帮助我们去处理结果（裁剪完的图像）
    CropHelper.handleResult(cropHandler,requestCode, resultCode, data);
  }
}
