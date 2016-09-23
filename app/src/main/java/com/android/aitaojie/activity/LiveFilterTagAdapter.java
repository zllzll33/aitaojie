package com.android.aitaojie.activity;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.LiveFilterFlowNaviadapter;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.custom.ZGradientDrawable;
import com.android.zlibrary.customView.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2016/8/5.
 */
public class LiveFilterTagAdapter extends ZModelAdapter<LiveModel.FilterBean>{
  private Map<String,String> tagMap;
    public LiveFilterTagAdapter(List<LiveModel.FilterBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
        tagMap=new HashMap<>();
    }
  public Map<String,String> getTagMap()
  {
      return tagMap;
  }
    @Override
    protected void BindModel(final int position, View convertView, ViewGroup parent,final LiveModel.FilterBean model) {

        FlowLayout flowLayout=(FlowLayout)convertView.findViewById(R.id.flow_specifi);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        title.setText(model.getLabel());
       final LiveFilterFlowNaviadapter liveFilterFlowNaviadapter=new LiveFilterFlowNaviadapter(model.getItem(),mActivity);
        for(int i=0;i<model.getItem().size();i++)
        {
            View view=liveFilterFlowNaviadapter.getView(i,null,null);
            flowLayout.addView(view);
            view.setOnClickListener(new onTagClickListener(i,view,model.getItem().get(i))
            {
                @Override
                public void onClick(View v) {
                    liveFilterFlowNaviadapter.setSelect(index);
                    tagMap.put(model.getName(),Itemmodel.getValue());
                }
            });
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_live_filter;
    }
    public class   onTagClickListener implements View.OnClickListener{
        protected int index;
        protected View view;
        protected   LiveModel.FilterBean.ItemBean Itemmodel;
        public onTagClickListener(int index,View view,LiveModel.FilterBean.ItemBean Itemmodel)
        {
            this.index=index;
            this.view=view;
            this.Itemmodel=Itemmodel;
        }
        @Override
        public void onClick(View v) {

        }
    }
}
