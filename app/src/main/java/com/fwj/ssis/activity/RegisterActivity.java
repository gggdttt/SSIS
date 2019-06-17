/*
 * Copyright(c)2019-
 *    项目名称:SSIS
 *    文件名称:RegisterActivity.java
 *    Date:19-6-3 下午9:04
 *    Author:Fanwj
 */

package com.fwj.ssis.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fwj.ssis.R;
import com.fwj.ssis.login_support.DrawableTextView;
import com.fwj.ssis.login_support.KeyboardWatcher;
import com.fwj.ssis.bean.tools.WebServicePost;

import java.io.UnsupportedEncodingException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener {
    private DrawableTextView logo;
    private EditText et_mobile;
    private EditText et_password;
    private EditText et_password1;
    private EditText et_name;
    private EditText et_school;
    private EditText et_testCode;
    private ImageView iv_clean_phone;
    private ImageView clean_password;
    private ImageView clean_password1;
    private ImageView iv_show_pwd;
    private ImageView iv_show_pwd1;
    private Button btn_register;
    private Button btn_getTestCode;
    private TextView forget_password;
    private ProgressDialog dialog;//登录进度条


    private int screenHeight = 0;//屏幕高度
    private float scale = 0.8f; //logo缩放比例
    private View service, body;
    private KeyboardWatcher keyboardWatcher;
    private String infoString;
    private View root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        initListener();
        btn_register.setEnabled(false);

        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);
    }

    private  void init ()
    {   clean_password1= findViewById(R.id.iv_show_pwd1);
        clean_password = findViewById(R.id.iv_show_pwd);
        logo = (DrawableTextView) findViewById(R.id.logo);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_name = (EditText) findViewById(R.id.et_getName);
        et_school = findViewById(R.id.et_getSchool);
        et_testCode = findViewById(R.id.et_getCode);
        iv_clean_phone = (ImageView) findViewById(R.id.iv_clean_phone);
        iv_show_pwd = (ImageView) findViewById(R.id.iv_show_pwd);
        iv_show_pwd1 = findViewById(R.id.iv_show_pwd1);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_getTestCode = findViewById(R.id.btn_getCode);
        body = findViewById(R.id.body);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        root = findViewById(R.id.root);
    }





    private void initListener() {
        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        iv_show_pwd1.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_getTestCode.setOnClickListener(this);


        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password.setSelection(s.length());
                }
            }
        });
        et_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && (clean_password1.getVisibility() == View.GONE)) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password1.setSelection(s.length());
                }
            }
        });
        et_password1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }
                else {

                    if (et_password.getText().toString().equals(et_password1.getText().toString()))
                    {
                        btn_register.setEnabled(true);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"两次密码不一致！", Toast.LENGTH_SHORT).show();
                        btn_register.setEnabled(false);
                    }
                }
            }
        });
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);

        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        if (view.getTranslationY()==0){
            return;
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }
    private boolean flag = false;
    private boolean flag1 = false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                et_mobile.setText("");
                break;
            case R.id.clean_password:
                et_password.setText("");
                break;
            case R.id.clean_password1:
                et_password1.setText("");
                break;
            case R.id.close:
                finish();
                break;
            case R.id.iv_show_pwd:
                if(flag == true){
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
                    flag = false;
                }else{
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                    flag = true;
                }
                String pwd = et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    et_password.setSelection(pwd.length());
                break;
            case R.id.iv_show_pwd1:
                if(flag1 == true){
                    et_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_show_pwd1.setImageResource(R.drawable.pass_gone);
                    flag1 = false;
                }else{
                    et_password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_show_pwd1.setImageResource(R.drawable.pass_visuable);
                    flag1 = true;
                }
                String pwd1 = et_password1.getText().toString();
                if (!TextUtils.isEmpty(pwd1))
                    et_password1.setSelection(pwd1.length());
                break;
            case R.id.btn_getCode:
                et_testCode.setText((int) (Math.random() * 10000)+"");
                break;
            case R.id.btn_register:
                if (et_password.getText().toString().equals("")||et_password1.getText().toString().equals("")
                ||et_testCode.getText().toString().equals("")||et_school.getText().toString().equals("")
                ||et_name.getText().toString().equals("")||et_mobile.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"请填全信息！", Toast.LENGTH_SHORT).show();
                    break;
                }
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setTitle("正在登陆");
                dialog.setMessage("请稍后");
                dialog.setCancelable(true);
                //设置可以通过back键取消
                dialog.show();
                //设置子线程，分别进行Get和Post传输数据
                new Thread(new MyThread()).start();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        Log.e("wenzhihao", "----->show" + keyboardSize);
        int[] location = new int[2];
        body.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        Log.e("wenzhihao","y = "+y+",x="+x);
        int bottom = screenHeight - (y+body.getHeight()) ;
        Log.e("wenzhihao","bottom = "+bottom);
        Log.e("wenzhihao","con-h = "+body.getHeight());
        if (keyboardSize > bottom){
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            zoomIn(logo, keyboardSize - bottom);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        Log.e("wenzhihao", "----->hide");
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", body.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        zoomOut(logo);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class MyThread implements Runnable{
        @Override
        public void run() {
            try {
                infoString = (String) WebServicePost.executeHttpPost(WebServicePost.getPost_data_regist(et_mobile.getText().toString()
                            ,et_password.getText().toString(),et_name.getText().toString(),
                        et_school.getText().toString()),"RegisterServlet");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("Fanwenjie:infoString",infoString==null?"null":infoString);
            showResponse(infoString);

        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            //更新UI
            @Override
            public void run() {
                if(response.equals("false")){
                    Toast.makeText(RegisterActivity.this,"注册失败！", Toast.LENGTH_SHORT).show();
                }else if (response.equals("true")){
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if (response.equals("exist"))
                {
                    Toast.makeText( RegisterActivity.this,"该号码已存在", Toast.LENGTH_SHORT).show();
                    et_mobile.setText("");
                }
                else
                {
                    Log.d("Fanwenjie:",response);
                    Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });
    }
}
