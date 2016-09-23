package com.android.zlibrary.letterlist;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.ViewUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by win7 on 2016/5/27.
 */
public class ZLetterListFragment extends ZBaseFragment {
    private ListView sortListView;
    private SideBar sideBar;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private TextView dialog;
    private ListListener listListener;
     public static int LetterMode=1;
    public static int AddressMode=1;
    public static int NameMode=2;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    @Override
    protected int layoutId() {
        return R.layout.frag_letterlist;
    }

    @Override
    protected void Init() {
        super.Init();
        initViews();
    }
    private void initViews() {
        // 实例化汉字转拼音类
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView)view. findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
//        SourceDateList = filledData(getResources().getStringArray(R.array.date));
        sortListView = (ListView)view. findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                ViewUtil.showToast(((SortModel) adapter.getItem(position)).getName());
                if(listListener!=null) {
                    String[] select=new String[2];
                    select[0]=((SortModel) adapter.getItem(position)).getName();
                    select[1]=((SortModel) adapter.getItem(position)).getCode();
                    listListener.OnClick(position,select);
                }
            }
        });



        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getActivity(), SourceDateList);
        sortListView.setAdapter(adapter);

        mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
}
    public void setLetterMode(int Mode)
    {
        LetterMode=Mode;
    }
   public void setData(List<SortModel>  sortModels) {
       SourceDateList = new ArrayList<SortModel>();
       characterParser = CharacterParser.getInstance();
       pinyinComparator = new PinyinComparator();
       for (int i = 0; i < sortModels.size(); i++) {
           SortModel sortModel = new SortModel();
           sortModel.setName(sortModels.get(i).getName());
           sortModel.setCode(sortModels.get(i).getCode());
           // 汉字转换成拼音
           String pinyin = characterParser.getSelling(sortModels.get(i).getName());
           String sortString = pinyin.substring(0, 1).toUpperCase();

           // 正则表达式，判断首字母是否是英文字母
           if (sortString.matches("[A-Z]")) {
               sortModel.setSortLetters(sortString.toUpperCase());
               SourceDateList.add(sortModel);
           } else {
               if(LetterMode==NameMode) {
                sortModel.setSortLetters("#");
                   SourceDateList.add(sortModel);
               }
           }

       }
   }
    private  List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                mSortList.add(sortModel);
            } else {
//                sortModel.setSortLetters("#");
//                mSortList.add(sortModel);
            }


        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    public void setListListener(ListListener listListener)
    {
        this.listListener=listListener;
    }
   public  interface  ListListener{
        public void OnClick(int index,String[] select);
    }
}
