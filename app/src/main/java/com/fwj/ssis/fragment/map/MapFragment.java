/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:MapFragment.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.fragment.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.fwj.ssis.R;
import com.laocaixw.layout.SuspendButtonLayout;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private String url = null;
    private WebView webView;
    private ProgressDialog dialog;
    private Activity mActivity;
    private ArrayList<String> urlList;
    SuspendButtonLayout suspendButtonLayout;//可移动按钮
    private  static  String HOME_URL = "https://vpn.njit.casbs.cn/client/#/login";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        urlList = new ArrayList<>();
        urlList.add(HOME_URL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        webView = (WebView) mActivity.findViewById(R.id.webview);
        webView.loadUrl(HOME_URL);
        suspendButtonLayout = mActivity.findViewById(R.id.btn_mapFragment_movable);
        suspendButtonLayout.setPosition(true,80);
        suspendButtonLayout.setOnSuspendListener(new SuspendButtonLayout.OnSuspendListener() {
            @Override
            public void onButtonStatusChanged(int status) {
                // 监听按钮状态：展开、关闭
                switch (status)
                {
                    case 0:

                        if(webView.canGoBack()){
                            webView.goBack();   //返回上一页面
                        }else {
                            Toast.makeText(mActivity,"已经到头了！", Toast.LENGTH_SHORT).show();

                        }

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




        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使网页可以再WebView中打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制网页在WebView中去打开，如果为false调用系统浏览器或者第三方浏览器打开

                view.loadUrl(url);
                return true;
            }//WebViewClient帮助WebView去处理一些页面控制和请求通知
        });

        //启用支持javaScript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress 1-100之间的整数
                if (newProgress == 100) {
                    //网页加载完毕,关闭ProgressDialog
                    closeDialo();
                } else {
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress);
                }
            }

            private void closeDialo() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(mActivity);
                    dialog.setTitle("加载中...");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }
        });

    }

    public MapFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


}
