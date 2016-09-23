package com.android.aitaojie.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.bmap.BMapManager;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.letterlist.SortModel;
import com.android.zlibrary.letterlist.ZLetterListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class CitySelectActivity extends BaseActivity {
    @InjectView(R.id.city_name)
    TextView cityName;
  private String city_code=Constant.CityCode,city_name;

    @Override
    protected int layoutId() {
        return R.layout.act_city_select;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("选择城市");
        cityName.setText(BMapManager.getInstance().getLocationModel().getCityName());
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("live");
        httpMap.putCtl("index");
        httpMap.putAct("citylist");
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson = new Gson();
                List<SortModel> sortModelList = gson.fromJson(data, new TypeToken<List<SortModel>>() {
                }.getType());
                ZLetterListFragment zLetterListFragment = new ZLetterListFragment();
                zLetterListFragment.setData(sortModelList);
                zLetterListFragment.setListListener(new ZLetterListFragment.ListListener() {
                    @Override
                    public void OnClick(int index, String[] select) {
                        city_code = select[1];
                        city_name=select[0];
                        cityName.setText(city_name);
                        Log.e("city_code", Constant.CityCode);
                    }
                });
                getZFragmentManager().replace(R.id.city_list, zLetterListFragment);
            }
        });


    }

    @Override
    protected void finishActivity() {
     if(!city_code.equals(Constant.CityCode))
     {
         Constant.CityCode=city_code;
         SDEventManager.post(city_name, EnumEventTag.CHANGE_LIVE_CITY.ordinal());
     }
        super.finishActivity();
    }
}
