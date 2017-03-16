package com.yyc.kiwifruitproject;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.yyc.kiwifruitproject.fragment.FragmentPage1;
import com.yyc.kiwifruitproject.fragment.FragmentPage2;
import com.yyc.kiwifruitproject.fragment.FragmentPage3;
import com.yyc.kiwifruitproject.fragment.FragmentPage4;


public class SelectActivity extends AppCompatActivity {

    //The FragmentTabHost Object
    private FragmentTabHost mTabHost;

    //Define a LayoutInflater
    private LayoutInflater layoutInflater;

    //Array for Fragments
    private Class fragmentArray[]={FragmentPage1.class, FragmentPage2.class, FragmentPage3.class,FragmentPage4.class};

    //Array for tab Images
    private int mImageViewArray[] ={R.drawable.icon_tab_gps,R.drawable.icon_tab_pic,R.drawable.icon_tab_query,R.drawable.icon_tab_tech};
    private String mTextArray[] = {"地图定位","图片欣赏","建议查询","技术学习"};
    private Toolbar toolbar;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        initToolBar();
        initView();
    }

    public void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setTitle(null);
        setSupportActionBar(toolbar);
    }

    /**
     * Init tab view
     */
    public void initView(){
        layoutInflater = LayoutInflater.from(this);
        mTitle = (TextView) findViewById(R.id.tv_title);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mTitle.setText(tabId);
            }
        });

        //The count of fragmentArray
        int count = fragmentArray.length;
        for (int i=0;i<count;i++){
            //set content for Tab
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
            //add tab content to FragmentTabHost
            mTabHost.addTab(tabSpec,fragmentArray[i],null);
            //set Tab Button Background
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.tabBackColor));
        }

    }

    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.item_tab_view,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_image);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_name);
        textView.setText(mTextArray[index]);
        return view;
    }
}


