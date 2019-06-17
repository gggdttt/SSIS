/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:GlobalVariables.java
 *    Date:19-6-4 下午12:26
 *    Author:Fanwj
 */

package com.fwj.ssis.fragment.spengding;

/**
 * Created by yuukidach on 17-3-21.
 */

public class GlobalVariables {
    private static String mDate = "";
    private static boolean mHasDot = false;
    private static String mInputMoney = "";
    private static String mDescription = "";
    private static int mBookId = 1;                         // 初始状态选择第一本账本
    private static int mBookPos = 0;

    public static void setmDate(String date)      { mDate = date;     }
    public static void setHasDot(boolean hasDot)  { mHasDot = hasDot; }
    public static void setmInputMoney(String a)   { mInputMoney = a;  }
    public static void setmDescription(String a ) { mDescription = a; }
    public static void setmBookId(int id)         { mBookId = id;     }
    public static void setmBookPos(int pos)       { mBookPos = pos;   }

    public static String getmDate()        { return mDate;        }
    public static boolean getmHasDot()     { return mHasDot;      }
    public static String getmInputMoney()  { return mInputMoney;  }
    public static String getmDescription() { return mDescription; }
    public static int getmBookId()         { return mBookId;      }
    public static int getmBookPos()        { return mBookPos;     }
}
