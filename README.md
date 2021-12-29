# SSIS

The ref library @ [UCount](https://github.com/yuukidach/Ucount) which is used to realize the function of recording bills.

Ref library @[okhttp](https://square.github.io/okhttp/)

Remember to add all the reference lib to gradle:

```yml
api 'com.jakewharton:butterknife:8.8.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1' //bufferknife
implementation'com.squareup.okhttp3:okhttp:3.10.0'//okhttp3
implementation'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1'//drawableText
implementation'com.laocaixw.suspendbuttonlayout:suspendbuttonlayout:1.0.3'//draw button
implementation 'com.android.support:cardview-v7:28.0.0'
implementation 'com.android.support:recyclerview-v7:28.0.0'
implementation'com.android.support:percent:28.0.0'
implementation'com.android.support:palette-v7:28.0.0'
implementation 'com.github.markushi:circlebutton:1.1'
implementation 'org.litepal.android:core:1.6.1'//litepal
implementation 'com.rengwuxian.materialedittext:library:2.1.4'
implementation 'com.github.rey5137:material:1.2.4'
implementation 'com.github.andyken:moneytextview:1.2'
implementation'com.jwenfeng.pulltorefresh:library:1.2.7'//pull refresh ListView
```

The Configuration of yml:

```yml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.fwj.ssis">
    <!--Chinese: 写外部存储即sdcard的权限 -->
    <!--English: Access to writing sd card-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".Myapplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:networkSecurityConfig="@xml/network_security_config"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning"
        tools:targetApi="n">
        <activity android:name=".activity.RegisterActivity" />
        <activity android:name=".fragment.spengding.AddItemActivity"/>
        <activity android:name=".fragment.spengding.AddDescription"/>
        <activity
            android:name=".activity.IndexActivity"
            android:label="@string/title_activity_index" />
        <activity
            android:name=".activity.LoginActivity"
            android:label="@string/title_activity_true_main" />
        <activity
            android:name=".activity.MainActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>


```

The configuration of `okHttp`:

```yml
 dependencies {
       // define a BOM and its version
       implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.2"))

       // define any required OkHttp artifacts without version
       implementation("com.squareup.okhttp3:okhttp")
       implementation("com.squareup.okhttp3:logging-interceptor")
    }
```





其中的记账功能的实现参考了https://github.com/yuukidach/Ucount 的设计以及部分图片
运用了okhttp实现和服务器端的通信
思路很简单，把需要请求的参数用get方式提交给服务器，当然有需要的同学可以把这部分改成对本地litepal进行存取的部分

效果：

