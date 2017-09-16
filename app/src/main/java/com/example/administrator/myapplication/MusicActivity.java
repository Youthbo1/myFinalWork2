package com.example.administrator.myapplication;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MusicActivity extends AppCompatActivity {

    private Button PlayOrPauseBtn,StopBtn;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView musicStatus, musicTime, musicTotal;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private boolean tag = false;
    private boolean tag1 = false;
    private boolean tag2 = false;

    //  通过 Handler 更新 UI 上的组件状态
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            musicTime.setText(time.format(mediaPlayer.getCurrentPosition()));
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            seekBar.setMax(mediaPlayer.getDuration());
            musicTotal.setText(time.format(mediaPlayer.getDuration()));
            handler.postDelayed(runnable, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        PlayOrPauseBtn = (Button)findViewById(R.id.BtnPlayorPause);
        StopBtn = (Button)findViewById(R.id.BtnStop);

        mediaPlayer = new MediaPlayer();
        seekBar = (SeekBar) findViewById(R.id.MusicSeekBar);
        musicTime = (TextView) findViewById(R.id.MusicTime);
        musicTotal = (TextView) findViewById(R.id.MusicTotal);
        musicStatus = (TextView) findViewById(R.id.MusicStatus);
        PlayOrPauseBtn.setOnClickListener(new mPlay());
        StopBtn.setOnClickListener(new mStop());



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    class mPlay implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            try{
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    seekBar.setMax(mediaPlayer.getDuration());
                }
                if(tag2 !=true){
                    mediaPlayer = MediaPlayer.create(MusicActivity.this, R.raw.music);
                    tag2 = true;
                }
                //  由tag的变换来控制事件的调用
                if (tag != true) {
                    PlayOrPauseBtn.setText("PAUSE");
                    musicStatus.setText("Playing");
                    tag = true;
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                } else {
                    PlayOrPauseBtn.setText("PLAY");
                    musicStatus.setText("Paused");
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                    tag = false;
                }
                if (tag1 == false) {
                    handler.post(runnable);
                    tag1 = true;
                }
            } catch (Exception e){  }
        }
    }

    class mStop implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            try{
                musicStatus.setText("Stopped");
                PlayOrPauseBtn.setText("PLAY");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                handler.removeCallbacks(runnable);
                tag = false;
                tag1 = false;
                tag2 = false;
            }catch (Exception ee){  }
        }
    }


}
