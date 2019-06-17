/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:PunchCommentItem.java
 *    Date:19-6-8 下午2:47
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.value;

public class PunchCommentItem {
    String userName;
    int userID;
    String comment;

    public PunchCommentItem(String userName, int userID, String comment, int punchRecordID) {
        this.userName = userName;
        this.userID = userID;
        this.comment = comment;
        this.punchRecordID = punchRecordID;
    }

    int punchRecordID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPunchRecordID() {
        return punchRecordID;
    }

    public void setPunchRecordID(int punchRecordID) {
        this.punchRecordID = punchRecordID;
    }
}
