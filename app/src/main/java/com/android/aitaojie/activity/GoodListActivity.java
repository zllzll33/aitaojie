package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.GoodTitleAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.fragment.GoodListFragment;
import com.android.aitaojie.model.ClassifyGoodListModel;
import com.android.zlibrary.customView.ZHorizontalScroll;
import com.android.zlibrary.customView.ZViewPagerFragment;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.TypeUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/20.
 */
public class GoodListActivity extends BaseActivity {
    List<ClassifyGoodListModel.ClassBean> titleList;
    List<Fragment> fragmentList;
    @InjectView(R.id.horizotalView)
    LinearLayout horizotalView;
    @InjectView(R.id.frag_good_list)
    FrameLayout fragGoodList;
    GoodTitleAdapter goodTitleAdapter;
    @InjectView(R.id.down)
    View down;
    @InjectView(R.id.scrollvew)
    ZHorizontalScroll scrollvew;
    @InjectView(R.id.tilte_scroll)
    LinearLayout tilteScroll;
    @InjectView(R.id.search)
    ImageView search;
    private int currenPisition, itemWidth, itemNum = 4;
    ClassifyGoodListModel classifyGoodListModel;
    ZViewPagerFragment zViewPagerFragment;

    @Override
    protected int layoutId() {
        return R.layout.act_good_list;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("商品列表");
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("Merchant");
        httpMap.putCtl("goods");
        httpMap.putAct("index");
        httpMap.putDataMap("jid", Constant.JID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson = new Gson();
                classifyGoodListModel = gson.fromJson(data, ClassifyGoodListModel.class);
                fragmentList = new ArrayList<Fragment>();
                titleList = classifyGoodListModel.getClassX();
                goodTitleAdapter = new GoodTitleAdapter(titleList, GoodListActivity.this);
                int width = SysUtil.getSreeenParam()[0];
                if (titleList.size() > itemNum) {
                    down.setVisibility(View.VISIBLE);
                    int scrollWidth = width - TypeUtil.dp2px(15);
                    itemWidth = scrollWidth / itemNum;
                    horizotalView.removeAllViews();
                    for (int i = 0; i < titleList.size(); i++) {
                        down.setVisibility(View.VISIBLE);
                        View view = goodTitleAdapter.getView(i, null, null);
                        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(scrollWidth / itemNum, TypeUtil.dp2px(50));
                        view.setLayoutParams(vlp);
                        view.setOnClickListener(new GoodTitleAdapter.ZOnClickListener(i, null, null) {
                            @Override
                            public void onClick(View v) {
                                Log.e("title_pistion", String.valueOf(position));
                                zViewPagerFragment.setCurrent(position);
                                goodTitleAdapter.setSelect(position);
                            }
                        });
                        horizotalView.addView(view);
                    }
                    goodTitleAdapter.setSelect(0);
                } else if (titleList.size() > 0) {
                    down.setVisibility(View.GONE);
                    horizotalView.removeAllViews();
                    for (int i = 0; i < titleList.size(); i++) {
                        View view = goodTitleAdapter.getView(i, null, null);
                        down.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(width / titleList.size(), TypeUtil.dp2px(50));
                        view.setLayoutParams(vlp);
                        view.setOnClickListener(new GoodTitleAdapter.ZOnClickListener(i, null, null) {
                            @Override
                            public void onClick(View v) {
                                Log.e("title_pistion", String.valueOf(position));
                                zViewPagerFragment.setCurrent(position);
                                goodTitleAdapter.setSelect(position);
                            }
                        });
                        horizotalView.addView(view);
                    }
                    goodTitleAdapter.setSelect(0);
                } else {
                    tilteScroll.setVisibility(View.GONE);
                }
                if (titleList.size() > 0) {

                    for (int i = 0; i < titleList.size(); i++) {
                        GoodListFragment goodFragment = new GoodListFragment();
                        goodFragment.setCid(titleList.get(i).getCid());
                        fragmentList.add(goodFragment);
                    }
                } else {
                    GoodListFragment goodFragment = new GoodListFragment();
                    goodFragment.setGoodList(classifyGoodListModel.getGoods());
                    fragmentList.add(goodFragment);
                }
                zViewPagerFragment = new ZViewPagerFragment(fragmentList);
                zViewPagerFragment.setPagerChangeListener(new ZViewPagerFragment.PagerChangeListener() {
                    @Override
                    public void onChange(int position) {

                        goodTitleAdapter.setSelect(position);
                        if (titleList.size() > itemNum) {

                            if ((currenPisition > itemNum - 1 && currenPisition != 0) || position > itemNum - 1)
                                scrollvew.scrollTo((position - itemNum + 1) * itemWidth, 0);
          /*          if (position > itemNum-1&&position>currenPisition)
                            scrollvew.scrollBy(itemWidth,0);
                    if(position<currenPisition&&currenPisition>itemNum-1)
                        scrollvew.scrollBy(-itemWidth,0);*/
                        }
                        currenPisition = position;

                    }
                });
                getZFragmentManager().atoggle(R.id.frag_good_list, zViewPagerFragment);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoodListActivity.this,GoodSearchActivity.class);
                startActivity(intent);
            }
        });
/*        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodListPop goodListPop = new GoodListPop(GoodListActivity.this);
                goodListPop.showAsDown(horizotalView);
            }
        });*/
    }

}
