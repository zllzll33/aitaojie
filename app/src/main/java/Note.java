import com.android.aitaojie.adapter.HomeGoodAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/7/20.
 */
public class Note {
/*
     <com.android.zlibrary.customView.SwipeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        app:clickToClose="true"
        android:id="@+id/sample1" >
        <LinearLayout
            android:tag="Bottom4"
            android:id="@+id/bottom_wrapper_2"
            android:layout_width="wrap_content"
            android:layout_height="match_parent">

            <ImageView
                android:id="@+id/magnifier2"
                android:layout_width="70dp"
                android:background="#f7e79c"
                android:paddingLeft="25dp"
                android:paddingRight="25dp"
                android:layout_height="match_parent" />
        </LinearLayout>

        <TextView
            android:padding="10dp"
            android:tag="Hover"
            android:text="要有最樸素的生活和最遙遠的夢想，即使明天天寒地凍，山高水遠，路遠馬亡。"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </com.android.zlibrary.customView.SwipeLayout>
 @InjectView(R.id.magnifier2)
    ImageView magnifier2;
    @InjectView(R.id.sample1)
    SwipeLayout sample1;
    @InjectView(R.id.bottom_wrapper_2)
    LinearLayout bottomWrapper2;
  sample1.setShowMode(SwipeLayout.ShowMode.PullOut);
    sample1.addDrag(SwipeLayout.DragEdge.Right,bottomWrapper2);
    sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Click on surface", Toast.LENGTH_SHORT).show();
        }
    });
    magnifier2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                sample1.close();  //关闭侧滑
            Toast.makeText(getActivity(), "magnifier", Toast.LENGTH_SHORT).show();
        }
    });
    */
/*
<com.android.zlibrary.customView.ZMoreGridView
            android:id="@+id/zgrid"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

                @InjectView(R.id.zgrid)
    ZMoreGridView zgrid;

  zgrid.setTitle("——新品包邮——");
    List<String> list=new ArrayList<String>();
    list.add("hello");
    list.add("hello");
    list.add("hello");
    list.add("hello");
    list.add("hello");
    list.add("hello");
    list.add("hello");
    list.add("hello");
    HomeGoodAdapter zMoreAdapter = new HomeGoodAdapter(list, getActivity());
    HomeGoodAdapter zNormalAdapter = new HomeGoodAdapter(zMoreAdapter.getSonList(5), getActivity());
    zgrid.setAdapter(zNormalAdapter, zMoreAdapter);*/
}
