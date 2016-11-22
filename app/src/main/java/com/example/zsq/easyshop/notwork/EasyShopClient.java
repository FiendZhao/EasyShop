package com.example.zsq.easyshop.notwork;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ZSQ on 2016/11/20.
 * 单例OkHttp
 */

public class EasyShopClient {
    private static EasyShopClient easyShopClient;
    private OkHttpClient okHttpClient;
    private EasyShopClient(){
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                //日志拦截器
                .addInterceptor(interceptor)
                .build();
    }
    public static EasyShopClient getInstance(){
        if (easyShopClient == null){
            easyShopClient = new EasyShopClient();
        }
        return easyShopClient;
    }
    /**
     * 注册
     *
     * post
     *
     * @param username 用户名
     * @param password 密码
     */
    public Call register(String username,String password){
        //表单形式构建请求
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.REGISTER)
                .post(requestBody) //ctrl+p查看参数
                .build();

        return okHttpClient.newCall(request);
    }
    /**
     * 登录
     * <p>
     * post
     *
     * @param username 用户名
     * @param password 密码
     */
    public Call login(String username, String password) {
        //表单形式构建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .post(requestBody) //ctrl+p查看参数
                .build();

        return okHttpClient.newCall(request);
    }
}
