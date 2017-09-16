package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import fragment.Gamefragment;
import fragment.Mainfragment;
import fragment.Musicfragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout mMenuMain;
    LinearLayout mMenuGame;
    LinearLayout  mMenuMusic;
//3个
    Mainfragment mMainfragment = new Mainfragment();
    Gamefragment mGamefragment = new Gamefragment();
    Musicfragment mMusicfragment = new Musicfragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //获取管理类
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content,mMainfragment)
                .add(R.id.container_content,mGamefragment)
                    .hide(mGamefragment)
                .add(R.id.container_content,mMusicfragment)
                    .hide(mMusicfragment)
                //事物添加  默认：显示首页  其他页面：隐藏
                //提交
                .commit();
    }
    public void initView(){
        mMenuMain= (LinearLayout) this.findViewById(R.id.menu_main);
        mMenuGame= (LinearLayout) this.findViewById(R.id.menu_game);
        mMenuMusic= (LinearLayout) this.findViewById(R.id.menu_music);

        mMenuMain.setOnClickListener(this);
        mMenuGame.setOnClickListener(this);
        mMenuMusic.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.menu_main://首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mMainfragment)
                        .hide(mGamefragment)
                        .hide(mMusicfragment)
                        .commit();
                break;
            case  R.id.menu_game://Game
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainfragment)
                        .show(mGamefragment)
                        .hide(mMusicfragment)
                        .commit();
                break;
            case  R.id.menu_music://music
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainfragment)
                        .hide(mGamefragment)
                        .show(mMusicfragment)
                        .commit();
                break;
        }
    }
}
