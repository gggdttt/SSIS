/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:WebServiceGet.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.bean.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 使用get方法获取Http服务器数据
 */

public class WebServiceGet {

    public static String path_login(String mobilePhone,String password,String address)
    {
        String Url = "http://"+SetServerIP.getIP()+":8080/SISSServer_war_exploded/" + address;
        String path = Url + "?mobilePhone=" + mobilePhone + "&password=" + password;
        return  path;
    }

    public static String executeHttpGet(String path){
        HttpURLConnection connection = null;
        InputStream in = null;

        try{
            try {

                URL url = new URL(path);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);//建立连接超时
                connection.setReadTimeout(8000);//传递数据超时
                in = connection.getInputStream();

                return parseInfo(in);
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
                response.append(line);
            }
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
