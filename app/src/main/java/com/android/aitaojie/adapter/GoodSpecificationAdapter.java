package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.LiveFilterTagAdapter;
import com.android.aitaojie.model.AddCartModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.adapter.ZNaviGridAdapter;
import com.android.zlibrary.custom.ZGradientDrawable;
import com.android.zlibrary.customView.FlowLayout;
import com.android.zlibrary.util.TypeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2016/7/22.
 */
public class GoodSpecificationAdapter extends ZModelAdapter<AddCartModel.SpceBean> {
    private Map<String,String> tagMap;
    SpecListener specListener;
    public GoodSpecificationAdapter(List<AddCartModel.SpceBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
        tagMap=new HashMap<>();
    }
    public Map<String,String> getTagMap()
    {
        return tagMap;
    }
    @Override
    protected void BindModel(final int position, View convertView, ViewGroup parent, final AddCartModel.SpceBean model) {
        FlowLayout specifi=(FlowLayout)convertView.findViewById(R.id.flow_specifi);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        title.setText(model.getName());
        final AddCartTagAdapter addCartTagAdapter=new AddCartTagAdapter(model.getItems(),mActivity);
        for(int i=0;i<model.getItems().size();i++)
        {
            View view=addCartTagAdapter.getView(i,null,null);
            specifi.addView(view);
            view.setOnClickListener(new AddCartTagAdapter.onTagClickListener(i,view,model.getItems().get(i))
            {
                @Override
                public void onClick(View v) {
                    addCartTagAdapter.setSelect(index);
                    tagMap.put("spec_id"+String.valueOf(position+1),Itemmodel.getId());
                    if(tagMap.size()==mlistModel.size())
                    {
                              if(specListener!=null)
                                  specListener.onSpec();
                    }
                }
            });
        }

    }
    @Override
    protected int layoutId() {
        return R.layout.item_good_specification;
    }
    public void setSpecListener(SpecListener specListener)
    {
        this.specListener=specListener;
    }
    public interface  SpecListener{
       public void onSpec();
    }
}
