# SSIS
打卡+记账+webView的校园生活安卓APP
    
    注意在app的build.gradle中加入依赖（如果你只需要部分功能的话）
    api 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1' //bufferknife不多说了……
    implementation'com.squareup.okhttp3:okhttp:3.10.0'//实现okhttp3
    implementation'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1'//drawableText效果
    implementation'com.laocaixw.suspendbuttonlayout:suspendbuttonlayout:1.0.3'//拖拽按钮的实现
    implementation 'com.android.support:cardview-v7:28.0.0'
    implementation 'com.android.support:recyclerview-v7:28.0.0'
    implementation'com.android.support:percent:28.0.0'
    implementation'com.android.support:palette-v7:28.0.0'
    implementation 'com.github.markushi:circlebutton:1.1'
    implementation 'org.litepal.android:core:1.6.1'//litepal
    implementation 'com.rengwuxian.materialedittext:library:2.1.4'
    implementation 'com.github.rey5137:material:1.2.4'
    implementation 'com.github.andyken:moneytextview:1.2'
    implementation'com.jwenfeng.pulltorefresh:library:1.2.7'//下拉刷新ListView




其中的记账功能的实现参考了https://github.com/yuukidach/Ucount 的设计以及部分图片
运用了okhttp实现和服务器端的通信
思路很简单，把需要请求的参数用get方式提交给服务器，当然有需要的同学可以把这部分改成对本地litepal进行存取的部分

效果：
