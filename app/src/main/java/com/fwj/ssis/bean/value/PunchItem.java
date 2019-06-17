/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:PunchItem.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.value;
public class PunchItem {
    int punchID;
    int userID;
    String date;
    String content;
    int type;
    int preferCount;
    int userLevel;
    String userName;
    String school;
    public PunchItem(int punchID, int userID, String date, String content, int type, int preferCount,int userLevel,String userName,String school) {
        this.punchID = punchID;
        this.userID = userID;
        this.date = date;
        this.content = content;
        this.type = type;
        this.preferCount = preferCount;
        this.userLevel = userLevel;
        this.userName = userName;
        this.school = school;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getPunchID() {
        return punchID;
    }

    public void setPunchID(int punchID) {
        this.punchID = punchID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPreferCount() {
        return preferCount;
    }

    public void setPreferCount(int preferCount) {
        this.preferCount = preferCount;
    }
}
