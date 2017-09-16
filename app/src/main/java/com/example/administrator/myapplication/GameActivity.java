package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Administrator on 2017/6/1 0120;
 */
public class GameActivity extends AppCompatActivity {
    private WuziqiPanal wuziqiPanal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wuziqiPanal = (WuziqiPanal) findViewById(R.id.id_wuziqi);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            wuziqiPanal.start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
