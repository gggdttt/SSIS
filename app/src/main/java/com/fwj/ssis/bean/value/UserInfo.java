/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:UserInfo.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.value;

public class UserInfo {
    int userID;
    String name;
    int level;
    String school;
    String mobilePhone;

    public  UserInfo()
    {

    }
    public UserInfo(int userID, String name,  int level, String school, String mobilePhone) {
        this.userID = userID;
        this.name = name;
        this.level = level;
        this.school = school;
        this.mobilePhone = mobilePhone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
