/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:WebServicePost.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 使用Post方法获取Http服务器数据
 */

public class WebServicePost {
    public static  String getPost_data_regist(String mobilePhone,String password,String name,String school) throws UnsupportedEncodingException {
        String data = "mobilePhone=" + URLEncoder.encode(mobilePhone,"UTF-8")
                + "&password=" + URLEncoder.encode(password,"UTF-8")
                +"&name="+URLEncoder.encode(name,"UTF-8")
                +"&school="+URLEncoder.encode(school,"UTF-8");
        Log.d("FanwenjiePsot:data",data);
        return data;
    }
     public static String executeHttpPost(String data,String address){
        HttpURLConnection connection = null;
        InputStream in = null;

        try{
            String Url = "http://"+SetServerIP.getIP()+":8080/SISSServer_war_exploded/" + address;
            try {
                URL url = new URL(Url);
                connection = (HttpURLConnection)url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(8000);//传递数据超时

                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                out.writeBytes(data);
                out.flush();
                out.close();


                int resultCode = connection.getResponseCode();
                if(HttpURLConnection.HTTP_OK == resultCode) {
                    in = connection.getInputStream();
                    return parseInfo(in);
                }
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时，连接关闭保护
            if(connection != null){
                connection.disconnect();
            }
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    //得到字节输入流，将字节输入流转化为String类型
    public static String parseInfo(InputStream inputStream){
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine()) != null){
                Log.d("RegisterActivity",line);
                response.append(line);
            }
            Log.d("RegisterActivity","response.toString():"+response.toString());
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}