package com.android.aitaojie.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.GoodTitleAdapter;
import com.android.aitaojie.adapter.GoodsListAdapter;
import com.android.aitaojie.adapter.LiveListAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.ClassifyGoodListModel;
import com.android.aitaojie.model.LiveModel;
import com.android.aitaojie.model.MyIndexModel;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/15.
 */
public class GoodSearchActivity extends BaseActivity {
    @InjectView(R.id.search)
    EditText search;
    @InjectView(R.id.grid)
    GridView grid;

    @Override
    protected int layoutId() {
        return R.layout.act_good_search;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("商品搜索");
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,                   KeyEvent event)  {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    if(!search.getText().toString().isEmpty())
                    {
                        httpSearch();
                    }
                    else
                        ViewUtil.showToast("请输入商品关键字");
                    return true;
                }
                return false;
            }
        });
    }
    private void  httpSearch()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("Merchant");
        httpMap.putCtl("goods");
        httpMap.putAct("search");
        httpMap.putDataMap("jid", Constant.JID);
        httpMap.putDataMap("key",search.getText().toString());
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                if(!data.isEmpty()) {
                    Gson gson = new Gson();
                    List<ClassifyGoodListModel.GoodsBean> goodsBean = gson.fromJson(data, new TypeToken<List<ClassifyGoodListModel.GoodsBean>>() {
                    }.getType());
                    SysUtil.hideKeyBoard(search);
                    GoodsListAdapter goodsListAdapter = new GoodsListAdapter(goodsBean, GoodSearchActivity.this);
                    grid.setAdapter(goodsListAdapter);
                }
                else
                {
                    ViewUtil.showToast("该字段没有搜到商品");
                }
            }
        });
    }


}

