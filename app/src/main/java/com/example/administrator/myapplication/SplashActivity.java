package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class SplashActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private Button mBtnSkip;
    private Runnable mRunnableToMain = new Runnable() {
        @Override
        public void run() {//时间到后跳转
            toFirstActivity();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBtnSkip= (Button) findViewById(R.id.btn_skip);
        mHandler.postDelayed(mRunnableToMain, 1000000);//显示时间设置
        //跳过按钮监听
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mRunnableToMain);
                toFirstActivity();
            }
        });
    }
    //跳转方法
    private void toFirstActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
