/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:PunchListAdapter.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fwj.ssis.Myapplication;
import com.fwj.ssis.R;
import com.fwj.ssis.bean.selfDefinedView.MyAddPunchReplyDialog;
import com.fwj.ssis.bean.selfDefinedView.MyDialog;
import com.fwj.ssis.bean.tools.SetServerIP;
import com.fwj.ssis.bean.value.PunchCommentItem;
import com.fwj.ssis.bean.value.PunchItem;
import com.fwj.ssis.bean.value.UserInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PunchListAdapter extends BaseAdapter {
    private List<PunchItem> list;
    private List<PunchCommentItem> commentItemList;
    private Context context;
    private Handler handler;
    private  int count = 0;
    private MyAddPunchReplyDialog myDialog;
    private Activity mActivity;
    private OkHttpClient client;
    public PunchListAdapter(List<PunchItem>list, List<PunchCommentItem> commentItemList, Context context, Handler handler, Activity activity,OkHttpClient client)
    {

        this.client = client;
        this.list = list;
        this.context = context;
        this.commentItemList = commentItemList;
        this.handler = handler;
        this.mActivity =activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * position从0开始
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * getview()方法有多少条数据就会执行多少次
     *
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        /*
         * 获得布局管理器的三种方式:
         * 1.LayoutInflater layoutInflater = LayoutInflater.from(context);
         * 2.LayoutInflater layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
         * 3.LayoutInflater inflater = getLayoutInflater();
         * */
        /*
         * listview优化：
         * 1.复用convertView
         * 2.使用ViewHolder
         * */
        ViewHolder viewHolder;
        if(view == null){
            // 1.获得布局管理器
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            // 2.布局
            view = inflater.inflate(R.layout.fragment_punch_item,null);

            viewHolder = new ViewHolder();
            // 3.获得控件
            viewHolder.iv_punchHeadImage = view.findViewById(R.id.iv_punchHeadImage);
            viewHolder.tv_punchContent = view.findViewById(R.id.tv_punchContent);
            viewHolder.tv_punchUserName = view.findViewById(R.id.tv_punchUserName);
            viewHolder.tv_punchTime = view.findViewById(R.id.tv_punchTime);
            viewHolder.iv_punchPrefer = view.findViewById(R.id.iv_punchPrefer);
            viewHolder.tv_punchType = view.findViewById(R.id.tv_punchType);
            viewHolder.tv_punchUserLevel = view.findViewById(R.id.tv_punchUserLevel);
            viewHolder.linearLayout = view.findViewById(R.id.punchCommentList);
            viewHolder.tv_addReply = view.findViewById(R.id.punch_reply);
            // 通过将setTag（）方法将viewHolder存放到convertView中
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        // 4.设置数据
        switch (list.get(position).getType())
        {
            case 0:
            {
                viewHolder.tv_punchType.setText("生活打卡");
                break;
            }
            case 1:
            {
                viewHolder.tv_punchType.setText("学习打卡");
                break;
            }

        }
        String name = list.get(position).getUserName();
        viewHolder.iv_punchHeadImage.setImageDrawable(TextDrawable.builder().beginConfig().withBorder(5).endConfig()
                .buildRoundRect(name.substring(name.length()-2),getColor(""+list.get(position).getSchool()+"-"+list.get(position).getUserName()), 20));
       // Log.d("FanWenJie Punch",list.get(position).getPreferCount()+"");
        if (list.get(position).getPreferCount()==0)viewHolder.iv_punchPrefer.setImageResource(R.drawable.prefer_normal);
        else  viewHolder.iv_punchPrefer.setImageResource(R.drawable.prefer_selected);
        viewHolder.tv_punchContent.setText(""+list.get(position).getContent());
        viewHolder.tv_punchTime.setText(""+list.get(position).getDate());
        viewHolder.tv_punchUserLevel.setText(""+list.get(position).getUserLevel());
        viewHolder.tv_punchUserName.setText(""+list.get(position).getSchool()+"-"+list.get(position).getUserName());

        for (PunchCommentItem item:this.commentItemList)
        {
            if (list.get(position).getPunchID()==item.getPunchRecordID()) {

               // Log.d("FanWenjieLog", "getView: " + list.get(position).getPunchID()+":"+item.getPunchRecordID());
            LinearLayout linearLayout = new LinearLayout(context);
            TextView textView_name = new TextView(context);
            TextView textView_comment = new TextView(context);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0, 5, 0, 5);

            textView_name.setPadding(20, 5, 5, 5);
            textView_name.setText(item.getUserName() + ":");
            textView_name.setTextColor(Color.parseColor("#2196F3"));
            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            textView_comment.setText(item.getComment());
            textView_comment.setPadding(20, 5, 5, 5);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(textView_name, param1);
            linearLayout.addView(textView_comment, param2);
            viewHolder.linearLayout.addView(linearLayout);
            item.setPunchRecordID(-1);
       }
        }


        //增加监听器

        viewHolder.tv_addReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new MyAddPunchReplyDialog(mActivity);
                myDialog.setTitle("回复");
                myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {

                    @Override
                    public void onYesClick() {
                        EditText editText = myDialog.findViewById(R.id.et_add_dialog_content);
                        String content = editText.getText().toString();
                        Myapplication myapplication =(Myapplication) mActivity.getApplication();
                        UserInfo userInfo = myapplication.getUserInfo();
                        getRequest_addPunchReply(userInfo.getUserID()+"",content,list.get(position).getPunchID()+"" );
                        handler.sendEmptyMessage(2);
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
            }
        });
        return view;
    }

    public static int getColor(String name) {
        String[] beautifulColors =
                new String[]{"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
                        "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154",
                        "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7",
                        "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29",
                        "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141",
                        "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178",
                        "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
                        "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128",
                        "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46",
                        "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113",
                        "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124",
                        "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224",
                        "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
                        "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67",
                        "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186",
                        "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
        int len = beautifulColors.length;
        String[] color = beautifulColors[Math.abs(name.hashCode())%len].split(",");
        return Color.rgb(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
                Integer.parseInt(color[2]));
    }


    class ViewHolder{
        ImageView iv_punchHeadImage,iv_punchPrefer;
        TextView tv_punchUserName,tv_punchType,tv_punchUserLevel,tv_punchContent,tv_punchTime;
        LinearLayout linearLayout;
        TextView tv_addReply;
    }


    private void getRequest_addPunchReply(String userID,String comment,String punchRecordID) {

        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url("http://"+ SetServerIP.getIP()+":8080/SISSServer_war_exploded/"+"AddPunchComment" +
                        "?userID="+userID+"&comment="+comment+"&punchRecordID="+punchRecordID)
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