package com.example.zsq.easyshop.notwork;

/**
 * Created by ZSQ on 2016/11/20.
 * 存放易淘项目的接口类
 */

public class EasyShopApi {

    //服务器地址
    static String BASE_URL = "http://wx.feicuiedu.com:9094/yitao/";

    //图片的根路径
    public static final String IMAGE_URL = "http://wx.feicuiedu.com:9094/";

    //注册接口
    static String REGISTER = "UserWeb?method=register";

    //登录接口
    static final String LOGIN = "UserWeb?method=login";

    //更新接口（更新昵称，更新头像）
    static final String UPDATA = "UserWeb?method=update";
}
