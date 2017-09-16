package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapplication.GameActivity;
import com.example.administrator.myapplication.MusicActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class Musicfragment extends Fragment {

    Button startMusic;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_music, container, false);
    }
    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startMusic = (Button) getView().findViewById(R.id.startMusic);
        startMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到游戏活动
                Intent s = new Intent(getActivity(), MusicActivity.class);
                startActivity(s);
            }
        });


    }
}