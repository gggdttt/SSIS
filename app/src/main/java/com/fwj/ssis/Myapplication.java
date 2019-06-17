/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:Myapplication.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis;

import com.fwj.ssis.bean.value.UserInfo;

import org.litepal.LitePalApplication;

public class Myapplication extends LitePalApplication {
    //声明一个变量
    private UserInfo userInfo;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        userInfo = new UserInfo();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
