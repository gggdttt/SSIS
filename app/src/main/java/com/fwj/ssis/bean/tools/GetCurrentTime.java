/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:GetCurrentTime.java
 *    Date:19-6-4 上午9:39
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentTime {
    public static String getCunnretTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss ");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String current = sdf.format(date);
        return current;
    }
}
