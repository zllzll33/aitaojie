package com.android.zlibrary.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.android.zlibrary.R;
import com.android.zlibrary.model.ThreePickerModel;
import com.android.zlibrary.customView.ZPickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/6/16.
 */
public class ZThreePickerDialog extends ZBaseDialog {
    List<ThreePickerModel.FirstModel> firstModelList;
    private View view;
    private ZPickerView first_picker, second_picker, third_picker;
    private String currentFirstModel, currentSecondModel, currentThirdModel;
   private int firstIndex, secondIndex, thirdIndex;
    private ZPickerDialogListener zPickerDialogListener;
    private int pickerNum;
    public ZThreePickerDialog() {
        super();
    }

    public ZThreePickerDialog(Context context) {
        super(context);
    }

    public ZThreePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initContentView(LinearLayout ll_content) {
        super.initContentView(ll_content);
        dialog_menu_tv_title.setVisibility(View.GONE);
        view = LayoutInflater.from(context).inflate(R.layout.zpicker_area, null, false);
        first_picker = (ZPickerView) view.findViewById(R.id.province);
        second_picker = (ZPickerView) view.findViewById(R.id.city);
        third_picker = (ZPickerView) view.findViewById(R.id.district);
        ll_content.addView(view);
    }
public ZThreePickerDialog setPickerNum(int pickerNum)
{
    this.pickerNum=pickerNum;
        switch (pickerNum)
        {
            case 1:
                first_picker.setVisibility(View.GONE);
                second_picker.setVisibility(View.GONE);
                break;
            case 2:
                first_picker.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    return this;
}
    public ZThreePickerDialog setThreePickerList(final List<ThreePickerModel.FirstModel> firstModelList)
    {
        this.firstModelList =firstModelList;
        List<String> provinces=new ArrayList<String>();
        for(int i=0;i<firstModelList.size();i++)
        {
            provinces.add(firstModelList.get(i).getName());
        }
        first_picker.setData(provinces);
        first_picker.setSelected(0);
        thirdIndex =0;
        currentFirstModel =firstModelList.get(0).getName();
        second_picker.setData(getSecondList(0));
        second_picker.setSelected(0);
        secondIndex =0;
        currentSecondModel =firstModelList.get(0).getSecondList().get(0).getName();
        third_picker.setData(getThirdList(0));
        third_picker.setSelected(0);
        thirdIndex =0;
        currentThirdModel =firstModelList.get(0).getSecondList().get(0).getThirdList().get(0).getName();
        first_picker.setOnSelectListener(new ZPickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                index=index%firstModelList.size();
                second_picker.setData(getSecondList(index));
                second_picker.setSelected(0);
                secondIndex =0;
                third_picker.setData(getThirdList(0));
                third_picker.setSelected(0);
                thirdIndex =0;
                currentFirstModel = firstModelList.get(firstIndex).getName();
                currentSecondModel =firstModelList.get(firstIndex).getSecondList().get(secondIndex).getName();
                currentThirdModel =firstModelList.get(firstIndex).getSecondList().get(secondIndex).getThirdList().get(thirdIndex).getName();
            }
        });
        second_picker.setOnSelectListener(new ZPickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                index=index%firstModelList.get(firstIndex).getSecondList().size();
                third_picker.setData(getThirdList(index));
                third_picker.setSelected(0);
                thirdIndex =0;
                currentSecondModel =firstModelList.get(firstIndex).getSecondList().get(secondIndex).getName();
                currentThirdModel =firstModelList.get(firstIndex).getSecondList().get(secondIndex).getThirdList().get(thirdIndex).getName();
            }
        });
        third_picker.setOnSelectListener(new ZPickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                index=index%firstModelList.get(firstIndex).getSecondList().get(secondIndex).getThirdList().size();
                thirdIndex =index;
                currentThirdModel =firstModelList.get(firstIndex).getSecondList().get(secondIndex).getThirdList().get(thirdIndex).getName();
            }
        });
        return this;
    }
    public List<String> getSecondList(int index)
    {
        firstIndex =index;
        List<String> cityList=new ArrayList<String>();
        List<ThreePickerModel.SecondModel> citys= firstModelList.get(index).getSecondList();
        for(int i=0;i<citys.size();i++)
            cityList.add(citys.get(i).getName());
        return  cityList;
    }
    public List<String> getThirdList(int index)
    {
        secondIndex =index;
        List<String> secondLists=new ArrayList<String>();
        List<ThreePickerModel.ThirdModel> areas= firstModelList.get(firstIndex).getSecondList().get(secondIndex).getThirdList();
        for(int i=0;i<areas.size();i++)
            secondLists.add(areas.get(i).getName());
        return  secondLists;
    }
    @Override
    public ZBaseDialog showDialog()
    {
       if(!isShowing()) {
           Window window = getWindow();
           window.setGravity(Gravity.BOTTOM);
           setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
           show();
       }
        return this;
    }
    @Override
    protected void onComfirm(View v) {
        String[] area=new String[3];
        area[0]= currentFirstModel;
        area[1]= currentSecondModel;
        area[2]= currentThirdModel;
            if (zPickerDialogListener != null)
                zPickerDialogListener.onComfirm(v,area ,ZThreePickerDialog.this);
            dismiss();
    }
    public ZThreePickerDialog setZAreaDialogListener(ZPickerDialogListener zPickerDialogListener)
    {
        this.zPickerDialogListener =zPickerDialogListener;
        return this;
    }
    public interface ZPickerDialogListener {
        public void onCancelClick(View v, ZBaseDialog dialog);
        public void onComfirm(View v,String[] texts, ZBaseDialog dialog);
    }
}