package com.android.zlibrary.adv;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.zlibrary.R;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/5/16.
 */
public abstract  class ZAdvFragment extends ZBaseFragment {
    AdvListener advListener;
    private CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<ImageView>();
    protected List<ADInfo> infos ;

    protected  int layoutId()
    {
        return R.layout.frag_adv;
    }

    protected void Init()
    {
        cycleViewPager=(CycleViewPager)getChildFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);
        initDots();
        configImageLoader();
        initAdv();
    }
    protected void initDots()
    {

    }
    public  void setAdInfoList(List<ADInfo> infos)
    {
        this.infos=infos;
    }
    protected ZAdvFragment setNormalDot(int res)
    {
        cycleViewPager.setNormalDot(res);
        return this;
    }
    protected ZAdvFragment setSelecteDot(int res)
    {
        cycleViewPager.setSelecteDot(res);
        return this;
    }
 private void initAdv()
 {

   if(infos==null)
       return;
     views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
     for (int i = 0; i < infos.size(); i++) {
         views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
     }

     // 将第一个ImageView添加进来
     views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

     // 设置循环，在调用setData方法前调用
     cycleViewPager.setCycle(true);

     // 在加载数据前设置是否循环
     cycleViewPager.setData(views, infos, mAdCycleViewListener);
     //设置轮播
     cycleViewPager.setWheel(true);

     // 设置轮播时间，默认5000ms
     cycleViewPager.setTime(2000);
     //设置圆点指示图标组居中显示，默认靠右
     cycleViewPager.setIndicatorCenter();
 }
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if(advListener!=null)
                advListener.OnClick(position);
            if (cycleViewPager.isCycle()) {

            }

        }

    };
    public void setAdvListener(AdvListener advListener)
    {
        this.advListener=advListener;
    }
    public  interface AdvListener{
        public void OnClick(int position);
    }
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.adv_image_timeout) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.adv_image_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.iadv_image_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
