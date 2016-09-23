package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.HomeCouponAdapter;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.zlibrary.customView.ZGridView;
import com.android.zlibrary.fragment.ZBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class HomeCouponFragment extends ZBaseFragment {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.grid)
    ZGridView grid;
private List<HomeIndexModel.VoucherBean> voucherBeanList;
    @Override
    protected int layoutId() {
        return R.layout.frag_home_coupon;
    }
  public void setCouponList(List<HomeIndexModel.VoucherBean> voucherBeanList)
  {
      this.voucherBeanList=voucherBeanList;
  }
    @Override
    protected void Init() {
        super.Init();
      if(voucherBeanList.size()==0)
          return;
        HomeCouponAdapter homeCouponAdapter=new HomeCouponAdapter(voucherBeanList,getActivity());
        grid.setAdapter(homeCouponAdapter);
    }
}
