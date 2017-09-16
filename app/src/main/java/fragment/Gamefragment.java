package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapplication.Game2;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.GameActivity;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class Gamefragment extends Fragment {
    Button start ,s2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_game,container,false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        start = (Button) getView().findViewById(R.id.startGame);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到游戏活动
                Intent s=new Intent(getActivity(), GameActivity.class);
                startActivity(s);
            }
        });
        s2 = (Button) getView().findViewById(R.id.startGame2);
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到游戏活动
                Intent s1=new Intent(getActivity(), Game2.class);
                startActivity(s1);
            }
        });
    }

}
