package com.android.zlibrary.custom;

import android.content.res.AssetManager;

import com.android.zlibrary.base.ZLibrary;
import com.android.zlibrary.model.ThreePickerModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by win7 on 2016/6/16.
 */
public class ZProvinceParser {
    private String mCurrentProviceName, mCurrentCityName,mCurrentDistrictName,mCurrentZipCode;
    private String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    List<ThreePickerModel.FirstModel> provinceList = null;
    private  static ZProvinceParser zProvinceParser;
    public static ZProvinceParser getInstance()
    {
        if(zProvinceParser==null)
            zProvinceParser=new ZProvinceParser();
        return zProvinceParser;


    }
    public  List<ThreePickerModel.FirstModel> getProvinceList()
    {
        return provinceList;
    }
    public ZProvinceParser()
    {
        initData();
    }
    public void initData()
{
    try {
        AssetManager asset = ZLibrary.getInstance().getApplication().getAssets();
        InputStream input = asset.open("province_data.xml");
//        InputStream input = ZLibrary.getInstance().getApplication().getResources().openRawResource(R.raw.province_data) ;
        // 创建一个解析xml的工厂对象
        SAXParserFactory spf = SAXParserFactory.newInstance();
        // 解析xml
        SAXParser parser = spf.newSAXParser();
        ProvinceHandler handler = new ProvinceHandler();
        parser.parse(input, handler);
        input.close();
        // 获取解析出来的数据
        provinceList = handler.getProvinceList();
        //*/ 初始化默认选中的省、市、区
        if (provinceList!= null && !provinceList.isEmpty()) {
            mCurrentProviceName = provinceList.get(0).getName();
            List<ThreePickerModel.SecondModel> cityList = provinceList.get(0).getSecondList();
            if (cityList!= null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<ThreePickerModel.ThirdModel> districtList = cityList.get(0).getThirdList();
                mCurrentDistrictName = districtList.get(0).getName();
                mCurrentZipCode = districtList.get(0).getCode();
            }
        }
        //*/
        mProvinceDatas = new String[provinceList.size()];
        for (int i=0; i< provinceList.size(); i++) {
            mProvinceDatas[i] = provinceList.get(i).getName();
            List<ThreePickerModel.SecondModel> cityList = provinceList.get(i).getSecondList();
            String[] cityNames = new String[cityList.size()];
            for (int j=0; j< cityList.size(); j++) {
                cityNames[j] = cityList.get(j).getName();
                List<ThreePickerModel.ThirdModel> districtList = cityList.get(j).getThirdList();
                String[] distrinctNameArray = new String[districtList.size()];
                ThreePickerModel.ThirdModel[] distrinctArray = new ThreePickerModel.ThirdModel[districtList.size()];
                for (int k=0; k<districtList.size(); k++) {
                    ThreePickerModel.ThirdModel districtModel = new ThreePickerModel.ThirdModel();
                    districtModel.setName(districtList.get(k).getName());
                    districtModel.setCode(districtList.get(k).getCode());
                    distrinctArray[k] = districtModel;
                    distrinctNameArray[k] = districtModel.getName();
                    mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getCode());
                }
                mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
            }
            mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        }
    } catch (Throwable e) {
        e.printStackTrace();
    } finally {

    }
}
    public List<ThreePickerModel.FirstModel> parse(InputStream is) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();  //取得SAXParserFactory实例
        SAXParser parser = factory.newSAXParser();                  //从factory获取SAXParser实例
        ProvinceHandler handler = new ProvinceHandler();                        //实例化自定义Handler
        parser.parse(is, handler);                                  //根据自定义Handler规则解析输入流
        return handler.getProvinceList();
    }
    public class ProvinceHandler extends DefaultHandler {
     private List<ThreePickerModel.FirstModel> provinceList;
        private ThreePickerModel.FirstModel provinceModel;
        private ThreePickerModel.SecondModel cityModel;
        private ThreePickerModel.ThirdModel districtModel;
        private StringBuilder builder;
        public List<ThreePickerModel.FirstModel> getProvinceList()
        {
            return provinceList;
        }
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            provinceList = new ArrayList<ThreePickerModel.FirstModel>();
            builder = new StringBuilder();
        }
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("province")) {
                provinceModel = new ThreePickerModel.FirstModel();
                provinceModel.setName(attributes.getValue(0));
                provinceModel.setSecondList(new ArrayList<ThreePickerModel.SecondModel>());
            } else if (qName.equals("city")) {
                cityModel = new ThreePickerModel.SecondModel();
                cityModel.setName(attributes.getValue(0));
                cityModel.setThirdList(new ArrayList<ThreePickerModel.ThirdModel>());
            } else if (qName.equals("district")) {
                districtModel = new ThreePickerModel.ThirdModel();
                districtModel.setName(attributes.getValue(0));
                districtModel.setCode(attributes.getValue(1));
            }
            builder.setLength(0);   //将字符长度设置为0 以便重新开始读取元素内的字符节点
        }
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            builder.append(ch, start, length);  //将读取的字符数组追加到builder中
        }
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            // 遇到结束标记的时候，会调用这个方法
            if (qName.equals("district")) {
                cityModel.getThirdList().add(districtModel);
            } else if (qName.equals("city")) {
                provinceModel.getSecondList().add(cityModel);
            } else if (qName.equals("province")) {
                provinceList.add(provinceModel);
            }
        }
    }



}
