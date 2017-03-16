package com.yyc.kiwifruitproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yyc.kiwifruitproject.MainActivity;
import com.yyc.kiwifruitproject.PicActivity;
import com.yyc.kiwifruitproject.R;
import com.yyc.kiwifruitproject.SelectActivity;

/**
 * Created by Regardo on 17/3/13.
 */

public class FragmentPage1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_1,null);

        Button button = (Button) layout.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPicActivity();
            }
        });

        return layout;
    }


    public void goPicActivity(){
        Intent intent = new Intent(getActivity(),PicActivity.class);
        startActivity(intent);
    }



}
