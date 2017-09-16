package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public class Game2 extends AppCompatActivity {
    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2);
        initBitmap();
    }

    public void initBitmap() {
        gameView = new GameView(this);
        this.setContentView(gameView);
    }

}
