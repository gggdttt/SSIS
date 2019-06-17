/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:PunchFragment.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.fragment.punch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fwj.ssis.Myapplication;
import com.fwj.ssis.R;
import com.fwj.ssis.adapter.PunchListAdapter;
import com.fwj.ssis.bean.selfDefinedView.MyDialog;
import com.fwj.ssis.bean.tools.GetCurrentTime;
import com.fwj.ssis.bean.tools.SetServerIP;
import com.fwj.ssis.bean.value.PunchCommentItem;
import com.fwj.ssis.bean.value.PunchItem;
import com.fwj.ssis.bean.value.UserInfo;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.laocaixw.layout.SuspendButtonLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PunchFragment extends Fragment {
    private List<PunchItem> punchItemArrayList = new ArrayList<>();
    private List<PunchCommentItem> punchCommnetItemList = new ArrayList<>();
    Activity mActivity;
    final OkHttpClient client = new OkHttpClient();
    PunchListAdapter adapter;
    Handler handler ;//控制多线程
    SuspendButtonLayout suspendButtonLayout;//可移动按钮
    private MyDialog myDialog;
    private PullToRefreshLayout pullToRefreshLayout;//下拉更新
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

    }



//    public void onAttach(Activity activity) {
//
//        super.onAttach(activity);
//        mActivity = activity;//保存activity引用
//    }


    public PunchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_punch_list, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onStart() {
        super.onStart();
        initList();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int count = 0;
                switch (msg.what){
                    case 0:
                        adapter = new PunchListAdapter(punchItemArrayList,punchCommnetItemList,mActivity,handler,mActivity,client);
                        ListView listView = getActivity().findViewById(R.id.punchList);
                        listView.setAdapter(adapter);
                        pullToRefreshLayout = getActivity().findViewById(R.id.pullToRefresh);
                        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
                            @Override
                            public void refresh() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initList();
                                        adapter.notifyDataSetChanged();
                                        // 结束刷新
                                        pullToRefreshLayout.finishRefresh();
                                    }
                                }, 2000);
                            }

                            @Override
                            public void loadMore() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 结束加载更多
                                        pullToRefreshLayout.finishLoadMore();
                                    }
                                }, 2000);
                            }
                        });
                        break;
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }

            }
        };

        // Inflate the layout for this fragment

        suspendButtonLayout= (SuspendButtonLayout) mActivity.findViewById(R.id.btn_punchFragment_movable);
        suspendButtonLayout.setPosition(true,80);
        suspendButtonLayout.setOnSuspendListener(new SuspendButtonLayout.OnSuspendListener() {
            @Override
            public void onButtonStatusChanged(int status) {
                // 监听按钮状态：展开、关闭
                switch (status)
                {
                    case 0:
                        myDialog = new MyDialog(mActivity);
                        myDialog.setTitle("打卡");
                        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {

                            @Override
                            public void onYesClick() {
                                RadioGroup radioGroup = myDialog.findViewById(R.id.rg_select_inputType);
                                int type=(radioGroup.getCheckedRadioButtonId())
                                                ==R.id.rb_punchtype_study?0:1;
                                EditText editText = myDialog.findViewById(R.id.et_add_dialog_content);
                                String content = editText.getText().toString();

                                Myapplication myapplication = (Myapplication) mActivity.getApplication();
                                UserInfo userInfo = myapplication.getUserInfo();
                                getRequest_addPunch(userInfo.getUserID()+"", GetCurrentTime.getCunnretTime(),content,type+"" );
                                Toast.makeText(mActivity,type+content,Toast.LENGTH_SHORT).show();
                                handler.sendEmptyMessage(1);
                                myDialog.dismiss();
                            }
                        });
                        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                               myDialog.dismiss();
                            }
                        });
                        myDialog.show();

                        break;
                    case 1:
                        suspendButtonLayout.closeSuspendButton();
                        break;

                }
            }

            @Override
            public void onChildButtonClick(int index) {
                // 监听子按钮的点击事件
            }
        });


    }

    private void initList() {
        punchItemArrayList = new ArrayList<>();
        punchCommnetItemList = new ArrayList<>();
        getRequest();
    }



    private void getRequest() {

        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url("http://"+SetServerIP.getIP()+":8080/SISSServer_war_exploded/"+"GetPunchItemServlet")
                .build();
        final Request request1=new Request.Builder()
                .get()
                .tag(this)
                .url("http://"+SetServerIP.getIP()+":8080/SISSServer_war_exploded/"+"GetPunchComment")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                Response response1 = null;
                try {
                    response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                         String[] getInfo = response.body().string().split(";;");
                        for(String s:getInfo)
                        {   //Log.i("FanWenjieGet","打印GET响应的数据：" +s);
                            if (!s.equals(""))
                            {
                                String[] str = s.split("&");
                                PunchItem punchItem = new PunchItem(Integer.parseInt(str[6]),
                                        Integer.parseInt(str[7]), str[5], str[4], Integer.parseInt(str[3]),
                                0,Integer.parseInt(str[1]),str[0],str[2]);
                                punchItemArrayList.add(punchItem);
                            }
                        }
//                        handler.sendEmptyMessageDelayed(0,200);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }

                    Log.d("FanWenjieLog", "run: "+request1.url());
                    response1 = client.newCall(request1).execute();
                    if (response1.isSuccessful())
                    {
                        String[] getComment = response1.body().string().split(";;");
                        for (String s:getComment)
                        {Log.d("FanWenjieLog", "run: "+s);
                            if (!s.equals(""))
                            {
                                String[] str = s.split("&");
                                PunchCommentItem punchCommentItem = new PunchCommentItem(str[0],
                                        Integer.parseInt(str[1]),str[2],Integer.parseInt(str[3]));
                                punchCommnetItemList.add(punchCommentItem);

                            }
                        }

                    }
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

   //     new Thread(new Runnable() {
//            @Override
//
//            public void run() {
//                Response response1 = null;
//                try {
//                    Log.d("FanWenjieLog", "run: "+request1.url());
//                    response1 = client.newCall(request1).execute();
//                    if (response1.isSuccessful())
//                    {
//                        String[] getComment = response1.body().string().split(";;");
//                        for (String s:getComment)
//                        {Log.d("FanWenjieLog", "run: "+s);
//                            if (!s.equals(""))
//                            {
//                                String[] str = s.split("&");
//                                PunchCommentItem punchCommentItem = new PunchCommentItem(str[0],
//                                        Integer.parseInt(str[1]),str[2],Integer.parseInt(str[3]));
//                                punchCommnetItemList.add(punchCommentItem);
//
//                            }
//                        }
//
//                    }
//                    handler.sendEmptyMessage(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } }).start();
    }


    private void getRequest_addPunch(String userID,String recordTime,String content ,String type) {

        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url("http://"+SetServerIP.getIP()+":8080/SISSServer_war_exploded/"+"AddPunchServlet" +
                        "?userID="+userID+"&recordTime="+recordTime+"&content="+content+"&type="+type)
                .build();
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        handler.sendEmptyMessage(1);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}

