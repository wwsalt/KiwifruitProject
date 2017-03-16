package com.yyc.kiwifruitproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class PicActivity extends AppCompatActivity {
    private ImageView iv =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pic);
        iv = (ImageView)findViewById(R.id.imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picsel, menu);
        return true;
    }

    //菜单选择函数
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
        }
        return super.onOptionsItemSelected(item);
    }



}
