package com.yyc.kiwifruitproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.action.IdentifyResultSpinner;
import com.esri.android.action.IdentifyResultSpinnerAdapter;
import com.esri.android.map.Callout;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.tasks.identify.IdentifyParameters;
import com.esri.core.tasks.identify.IdentifyResult;
import com.esri.core.tasks.identify.IdentifyTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MapActivity extends Activity {
    MapView mv = null;
    ArcGISLocalTiledLayer local = null;
    IdentifyParameters params = null;//参数
    // create UI objects
    static ProgressDialog dialog;

    private Toast toast;
    //public BDLocationListenerImpl myListener = new BDLocationListenerImpl();

    //定位SDK的核心类
    //是否手动触发请求定位

    int requestTime = 0;
   String loacltxt = "file:///storage/sdcard0/zhouzh.tpk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapviewlayout);
        mv = (MapView) findViewById(R.id.gismap);
        //本地服务
        local = new ArcGISLocalTiledLayer("file:///storage/sdcard0/zhouzh.tpk");
        ;//11mv.addLayer(local)1.114.135.202
        //切片服务http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer
        ArcGISTiledMapServiceLayer tiledMapServiceLayer1 = new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        mv.addLayer(tiledMapServiceLayer1);

        /*ArcGISDynamicMapServiceLayer tiledMapServiceLayer = new ArcGISDynamicMapServiceLayer("http://111.114.135.202:6080/arcgis/rest/services/zhouzhi/zhouzhi/MapServer");
        mv.addLayer(tiledMapServiceLayer);*/

        mv.addLayer(new ArcGISTiledMapServiceLayer(this.getResources()
                .getString(R.string.identify_task_url_for_avghouseholdsize)));//添加切片
        mv.addLayer(local);
        // set Identify Parameters
        params = new IdentifyParameters();//识别位置
        params.setTolerance(20);
        params.setDPI(98);
        params.setLayers(new int[]{4});
        params.setLayerMode(IdentifyParameters.ALL_LAYERS);


     // Identify on single tap of map
     		mv.setOnSingleTapListener(new OnSingleTapListener() {

     			private static final long serialVersionUID = 1L;

     			public void onSingleTap(final float x, final float y) {

     				if (!mv.isLoaded()) {
     					return;
     				}

     				// Add to Identify Parameters based on tapped location
     				Point identifyPoint = mv.toMapPoint(x, y);

     				params.setGeometry(identifyPoint);
     				params.setSpatialReference(mv.getSpatialReference());
     				params.setMapHeight(mv.getHeight());
     				params.setMapWidth(mv.getWidth());
     				params.setReturnGeometry(false);

     				// add the area of extent to identify parameters
     				Envelope env = new Envelope();
     				mv.getExtent().queryEnvelope(env);
     				params.setMapExtent(env);

     				// execute the identify task off UI thread
     				MyIdentifyTask mTask = new MyIdentifyTask(identifyPoint);
     				mTask.execute(params);
     			}

     		});

     	}
    //显示窗口
    private ViewGroup createIdentifyContent(final List<IdentifyResult> results) {

        // create a new LinearLayout in application context
        LinearLayout layout = new LinearLayout(this);

        // view height and widthwrap content
        layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        // default orientation
        layout.setOrientation(LinearLayout.HORIZONTAL);

        // Spinner to hold the results of an identify operation
        IdentifyResultSpinner spinner = new IdentifyResultSpinner(this, results);

        // make view clickable
        
        //spinner.setClickable(false);
		//spinner.canScrollHorizontally(BIND_ADJUST_WITH_ACTIVITY);

        // MyIdentifyAdapter creates a bridge between spinner and it'icon_tab_tech data
        MyIdentifyAdapter adapter = new MyIdentifyAdapter(this, results);
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        layout.addView(spinner);

        return layout;
    }

    /**
     * This class allows the user to customize the string shown in the callout.
     * By default its the display field name.
     *
     * A spinner adapter defines two different views; one that shows the data in
     * the spinner itself and one that shows the data in the drop down list when
     * spinner is pressed.
     *
     */
    public class MyIdentifyAdapter extends IdentifyResultSpinnerAdapter {
        String m_show = null;
        List<IdentifyResult> resultList;
        int currentDataViewed = -1;
        Context m_context;

        public MyIdentifyAdapter(Context context, List<IdentifyResult> results) {
            super(context, results);
            this.resultList = results;
            this.m_context = context;
        }

        // Get a TextView that displays identify results in the callout.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String LSP = System.getProperty("line.separator");
            StringBuilder outputVal = new StringBuilder();

            // Resource Object to access the Resource fields
            Resources res = getResources();

            // Get Name attribute from identify results
            IdentifyResult curResult = this.resultList.get(position);

            if (curResult.getAttributes().containsKey(
                    res.getString(R.string.NAME))) {
                outputVal.append("Place: "
                        + curResult.getAttributes()
                        .get(res.getString(R.string.NAME)).toString());
                outputVal.append(LSP);
            }

            if (curResult.getAttributes().containsKey(
                    res.getString(R.string.ID))) {
                outputVal.append("State ID: "
                        + curResult.getAttributes()
                        .get(res.getString(R.string.ID)).toString());
                outputVal.append(LSP);
            }

            if (curResult.getAttributes().containsKey(
                    res.getString(R.string.ST_ABBREV))) {
                outputVal.append("Abbreviation: "
                        + curResult.getAttributes()
                        .get(res.getString(R.string.ST_ABBREV))
                        .toString());
                outputVal.append(LSP);
            }

            if (curResult.getAttributes().containsKey(
                    res.getString(R.string.TOTPOP_CY))) {
                outputVal.append("Population: "
                        + curResult.getAttributes()
                        .get(res.getString(R.string.TOTPOP_CY))
                        .toString());
                outputVal.append(LSP);

            }

            if (curResult.getAttributes().containsKey(
                    res.getString(R.string.LANDAREA))) {
                outputVal.append("Area: "
                        + curResult.getAttributes()
                        .get(res.getString(R.string.LANDAREA))
                        .toString());
                outputVal.append(LSP);

            }
            else
            {
            	//String SHIFEI= "当前地块施肥建议如下：" +"\n"+"单位：（千克/亩）"+"\n"+"有机肥:2000"+"\n"+"纯氮:15"+"\n"+"纯五氧化二磷:16"+"\n"+"纯氧化钾:15";
            	//outputVal.append(SHIFEI);
            }
            String SHIFEI="当前地块施肥建议如下：" +"\n"+"单位：（千克/亩）"+"\n"+"有机肥:2000"+"\n"+"纯氮:15"+"\n"+"纯五氧化二磷:16"+"\n"+"纯氧化钾:15";
            outputVal.append(SHIFEI);

            // Create a TextView to write identify results
            TextView txtView;
            txtView = new TextView(this.m_context);
            txtView.setText(outputVal);
            txtView.setTextColor(Color.BLACK);
            txtView.setLayoutParams(new ListView.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            txtView.setGravity(Gravity.CENTER_VERTICAL);
            return txtView;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.unpause();
    }

    private class MyIdentifyTask extends
            AsyncTask<IdentifyParameters, Void, IdentifyResult[]> {
        IdentifyTask task = new IdentifyTask(MapActivity.this.getResources().getString(R.string.identify_task_url_for_avghouseholdsize));

        IdentifyResult[] M_Result;

        Point mAnchor;

        MyIdentifyTask(Point anchorPoint) {
            mAnchor = anchorPoint;
        }

        @Override
        protected void onPreExecute() {
            // create dialog while working off UI thread
            dialog = ProgressDialog.show(MapActivity.this, "查询任务", "查询中 ...");

        }

        protected IdentifyResult[] doInBackground(IdentifyParameters... params) {

            // check that you have the identify parameters
            if (params != null && params.length > 0) {
                IdentifyParameters mParams = params[0];

                try {
                    // Run IdentifyTask with Identify Parameters

                    M_Result = task.execute(mParams);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return M_Result;
        }

        @Override
        protected void onPostExecute(IdentifyResult[] results) {

            // dismiss dialog
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ArrayList<IdentifyResult> resultList = new ArrayList<IdentifyResult>();

            IdentifyResult result_1;

            for (int index = 0; index < results.length; index++) {

                result_1 = results[index];
                String displayFieldName = result_1.getDisplayFieldName();
                Map<String, Object> attr = result_1.getAttributes();
                for (String key : attr.keySet()) {
                    if (key.equalsIgnoreCase(displayFieldName)) {
                        resultList.add(result_1);
                    }
                }
            }
            Callout callout = mv.getCallout();
            callout.setContent(createIdentifyContent(resultList));
            callout.show(mAnchor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //菜单选择函数
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Action_getloc:
            	//Intent i5 = new Intent(MapActivity.this,Activity2.class);
             	//startActivity(i5);
                return true;
            case R.id.Action_backto:
                Toast.makeText(MapActivity.this,  "返回", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
