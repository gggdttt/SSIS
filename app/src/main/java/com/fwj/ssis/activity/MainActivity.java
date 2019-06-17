/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:MainActivity.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;


import com.fwj.ssis.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        //隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //隐藏虚拟按键
        hideBottomUIMenu();
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.video_view);
        Button skip = (Button)findViewById(R.id.view_skip);
        skip.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        {
            initVideoPath();
        }


        handler =  new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //实现页面跳转
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                //跳转之后杀死之前留下的activity
                startActivity(intent);
                finish();
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0,10000);//表示延迟3秒发送任务



    }
    private void initVideoPath()
    {
//        File file = new File(Environment.getExternalStorageDirectory(),"../../0AFE-221D/Movies/move.mp4");
//        File file = new File("/storage/1406-1E1E/move.mp4");
        File file = new File("/sdcard/move.mp4");

        videoView.setVideoPath(file.getPath());
        videoView.start();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    initVideoPath();
                }
                else
                {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.view_skip:
                toMainActive();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (videoView != null)
        {
            videoView.suspend();
        }
    }

    //跳转
    public void toMainActive(){
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        //跳转之后杀死之前留下的activity
        startActivity(intent);
        handler.removeMessages(0);
        finish();
    }


    //隐藏虚拟按键，并且全屏
    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}

