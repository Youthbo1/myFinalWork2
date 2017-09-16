package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.administrator.myapplication.GifActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class Mainfragment extends Fragment {
    private Button b;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }
    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = (Button) getView().findViewById(R.id.startGif);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到 Gif
                Intent s=new Intent(getActivity(), GifActivity.class);
                startActivity(s);
            }
        });


}}
