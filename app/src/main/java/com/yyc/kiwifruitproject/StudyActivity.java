package com.yyc.kiwifruitproject;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import com.yyc.kiwifruitproject.R;


public class StudyActivity extends Activity {
    WebView wv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studylayout);
        wv = (WebView)findViewById(R.id.webView);
        wv.loadUrl("http://www.029mht.com/");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.websel, menu);
        return true;
    }
    //菜单选择函数
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Action_zhouzhi:
                wv = (WebView)findViewById(R.id.webView);
                wv.loadUrl("http://www.029mht.com/");
                return true;
            case R.id.Action_nongye:
                wv = (WebView)findViewById(R.id.webView);
                wv.loadUrl("http://www.xaagri.gov.cn/");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
