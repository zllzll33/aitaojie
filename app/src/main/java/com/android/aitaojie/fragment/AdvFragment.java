package com.android.aitaojie.fragment;

import android.view.View;


import com.android.aitaojie.R;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.zlibrary.adv.ADInfo;
import com.android.zlibrary.adv.ZAdvFragment;

import java.util.List;

/**
 * Created by win7 on 2016/6/14.
 */
public class AdvFragment extends ZAdvFragment {
    private List<HomeIndexModel.SlideBean> slideBeanList;
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg"
    };

    @Override
    protected void initDots() {
        super.initDots();
        setNormalDot(R.mipmap.point_normal);
        setSelecteDot(R.mipmap.point_select);
    }


    @Override
    protected void Init() {
        super.Init();
    }

  /*  public void setAdInfoList(List<ADInfo> infos) {

        for(int i=0;i<slideBeanList.size();i++)
        {
            ADInfo info = new ADInfo();
            info.setUrl(slideBeanList.get(i).getImg());
//            info.setContent("图片-->" + i);
            infos.add(info);
        }
     *//*   for(int i = 0; i < imageUrls.length; i ++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }*//*
    }
*/

}
