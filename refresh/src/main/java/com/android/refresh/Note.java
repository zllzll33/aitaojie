package com.android.refresh;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by win7 on 2016/8/12.
 */
public class Note {
/*    <MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:wave_color="#20ff2020"
    app:progress_show_circle_backgroud="true"
    app:overlay="false"
    app:wave_show="true"
    app:progress_backgroud_color="#FFFAFAFA"
    app:progress_colors="@array/material_colors"
    app:wave_height_type="normal"
    app:progress_show_arrow="true"
            >

    <ListView
    android:id="@+id/lv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"></ListView>


    </MaterialRefreshLayout>*/
/*    public class AutoRefreshActivity extends BaseActivity {
        private MaterialRefreshLayout materialRefreshLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listview);
            String[] array = new String[50];
            for (int i = 0; i < array.length; i++) {
                array[i] = "啊哈哈哈哈哈，啊哈哈 " + i;
            }
            final ListView listView = (ListView) findViewById(R.id.lv);
            listView.setAdapter(new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
            materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
            materialRefreshLayout.setLoadMore(true);
            materialRefreshLayout.finishRefreshLoadMore();
            materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
                @Override
                public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                    materialRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialRefreshLayout.finishRefresh();

                        }
                    }, 3000);

                }

                @Override
                public void onfinish() {
                    Toast.makeText(AutoRefreshActivity.this, "finish", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                    Toast.makeText(AutoRefreshActivity.this, "load more", Toast.LENGTH_LONG).show();
                }
            });


            materialRefreshLayout.autoRefresh();

        }
    }*/
}
