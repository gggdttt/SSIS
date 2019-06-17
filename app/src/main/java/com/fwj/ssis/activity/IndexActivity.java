/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:IndexActivity.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fwj.ssis.R;
import com.fwj.ssis.fragment.map.MapFragment;
import com.fwj.ssis.fragment.personInfo.PersonInfoFragment;
import com.fwj.ssis.fragment.punch.PunchFragment;
import com.fwj.ssis.fragment.spengding.SpendingFragment;

public class IndexActivity<navigationIndex> extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
//        implements MapFragment.OnFragmentInteractionListener, SpendingFragment.OnFragmentInteractionListener, PersonInfoFragment.OnFragmentInteractionListener
{
    PunchFragment punchFragment;
    SpendingFragment spendingFragment;
    MapFragment mapFragment;
    PersonInfoFragment personInfoFragment;
    int currentNavigateIndex  =  0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
    }



    public void initView(){
        // 1.
        punchFragment = new PunchFragment();
        spendingFragment = new SpendingFragment();
        mapFragment = new MapFragment();
        personInfoFragment = new PersonInfoFragment();
        // 2.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 3.
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        // 4.在指定layout布局中的的一块内容替换成fragment
        fragmentTransaction.add(R.id.indexFragmentTabHost,punchFragment);
        // 5.
        fragmentTransaction.commit();
    }
    protected  void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        // 3.
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        // 4.在指定layout布局中的的一块内容替换成fragment
        fragmentTransaction.replace(R.id.indexFragmentTabHost,fragment);
        // 5.
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_punch:
                if (currentNavigateIndex == 0)
                    return true;
                else
                {replaceFragment(new PunchFragment());
                    currentNavigateIndex = 0;}
                return true;
            case R.id.navigation_spending:
                if (currentNavigateIndex == 1)
                    return true;
                else
                {
                    replaceFragment(spendingFragment);
                    currentNavigateIndex = 1;
                }
                return true;
            case R.id.navigation_mapping:
                if (currentNavigateIndex == 2)
                    return true;
                else
                {
                    replaceFragment(mapFragment);
                    currentNavigateIndex = 2;
                }
                return true;
            case R.id.navigation_personInfo:
                if (currentNavigateIndex == 3)
                    return true;
                else
                {
                    replaceFragment(personInfoFragment);
                    currentNavigateIndex = 3;
                }
                return true;
        }
        return false;
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
