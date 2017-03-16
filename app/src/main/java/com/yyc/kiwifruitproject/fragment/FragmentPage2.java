package com.yyc.kiwifruitproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yyc.kiwifruitproject.R;

/**
 * Created by Regardo on 17/3/13.
 */

public class FragmentPage2 extends Fragment {

    private ImageView iv =null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic,null);

        iv = (ImageView)view.findViewById(R.id.imageView);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.picsel,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Action_mapzz:
                iv.setImageResource(R.drawable.quhua);
                return true;
            case R.id.Action_soil:
                iv.setImageResource(R.drawable.soil);
                return true;
            case R.id.Action_om:
                iv.setImageResource(R.drawable.om);
                return true;
            case R.id.Action_n:
                iv.setImageResource(R.drawable.n);
                return true;
            case R.id.Action_p:
                iv.setImageResource(R.drawable.p);
                return true;
            case R.id.Action_k:
                iv.setImageResource(R.drawable.k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
