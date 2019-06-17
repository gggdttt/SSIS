/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:LoadFragmentActivity.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fwj.ssis.R;

/**
 * Created by wenzhihao on 2017/8/18.
 */

public class LoadFragmentActivity extends FragmentActivity {
    private FragmentManager manager;
    private static Class<?> mCls ;
    private Fragment target ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_fragment);
        //设置Fragment管理器
        manager = getSupportFragmentManager() ;
        if (mCls == null) {
            return;
        }
        try {
            target = (Fragment) mCls.newInstance();
            target.setArguments(getIntent().getExtras());
            switchContent(target);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开Fragment
     * @param context
     * @param target
     * @param bundle
     */
    public static void lunchFragment(Context context, Class<?> target, Bundle bundle){
        mCls = target;
        Intent intent = new Intent(context, LoadFragmentActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private Fragment current;

    /**
     * 切换当前显示的fragment
     */
    public void switchContent(Fragment to) {
        if (current != to) {
            FragmentTransaction transaction = manager.beginTransaction();

            if (current != null) {
                transaction.hide(current);
            }
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.add(R.id.contentPanel, to).commit();
            } else {

                transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            current = to;
        }
    }

}
