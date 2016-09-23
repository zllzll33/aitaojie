package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MyCouponAdapter;
import com.android.zlibrary.fragment.ZBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyCouponListFragment extends ZBaseFragment {
    @InjectView(R.id.grid_coupon)
    GridView gridCoupon;

    @Override
    protected int layoutId() {
        return R.layout.frag_my_coupon_list;
    }

    @Override
    protected void Init() {
        super.Init();
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("hello");
        list.add("hello");
        list.add("hello");
        MyCouponAdapter myCouponAdapter = new MyCouponAdapter(list, getActivity());
        gridCoupon.setAdapter(myCouponAdapter);
    }


}
