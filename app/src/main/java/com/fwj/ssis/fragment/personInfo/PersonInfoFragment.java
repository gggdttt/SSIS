/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:PersonInfoFragment.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.fragment.personInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fwj.ssis.Myapplication;
import com.fwj.ssis.R;
import com.fwj.ssis.adapter.PunchListAdapter;
import com.fwj.ssis.bean.value.UserInfo;


public class PersonInfoFragment extends Fragment {

    private Activity mActivity;
    private ImageView circleImageView;
    private TextView userName;
    private TextView userlevel;
    public PersonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        Myapplication myapplication = (Myapplication) mActivity.getApplication();
        UserInfo userInfo = myapplication.getUserInfo();
        String name = userInfo.getName();
        int level = userInfo.getLevel();
        circleImageView.setImageDrawable(TextDrawable.builder().beginConfig().withBorder(5).endConfig()
                .buildRoundRect(name.substring(name.length()-2), PunchListAdapter.getColor(""+name), 20));
        userName.setText(name);
        userlevel.setText("Level:"+level);

    }

    public void init()
    {
        circleImageView = mActivity.findViewById(R.id.me_headview);
        userName = mActivity.findViewById(R.id.tv_user_name);
        userlevel = mActivity.findViewById(R.id.tv_me_level);
    }


}

