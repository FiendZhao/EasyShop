package com.example.zsq.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZSQ on 2016/11/21.
 */

public class User {
    @SerializedName("name")
    private String hx_Id;

    @SerializedName("uuid")
    private String table_Id;

    @SerializedName("username")
    private String name;
    @SerializedName("other")
    private String head_Image;
    @SerializedName("nickname")
    private String nick_name;
    private String password;

    public String getHx_Id() {
        return hx_Id;
    }

    public String getTable_Id() {
        return table_Id;
    }

    public String getName() {
        return name;
    }

    public void setHx_Id(String hx_Id) {
        this.hx_Id = hx_Id;
    }

    public void setTable_Id(String table_Id) {
        this.table_Id = table_Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_Image() {
        return head_Image;
    }

    public void setHead_Image(String head_Image) {
        this.head_Image = head_Image;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
