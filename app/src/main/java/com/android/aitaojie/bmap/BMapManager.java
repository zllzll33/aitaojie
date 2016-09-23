package com.android.aitaojie.bmap;

import com.android.aitaojie.config.App;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by win7 on 2016/6/3.
 */
public class BMapManager {
    private static BMapManager bMapManager;
    private LocationService locationService;
    private LocationModel locationModel;
   private   LocationSuccessListener locationSuccessListener;
    public BMapManager(){
        locationService = new LocationService(App.getInstance());
        locationModel=new LocationModel();
    }
    public static BMapManager getInstance()
    {
        if(bMapManager==null)
            bMapManager=new  BMapManager();
        return bMapManager;
    }
    public void startBMapService()
    {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        locationService.registerListener(mListener);
    }
    public void stopBMapService()
    {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }
    public void startLocation()
    {
        locationService.start();
    }
    public void stopLocation()
    {
        locationService.stop();
    }
    public LocationModel getLocationModel()
    {
        return locationModel;
    }
    public void setLocationSuccessListener(LocationSuccessListener locationSuccessListener)
    {
        this.locationSuccessListener=locationSuccessListener;
    }
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                locationModel.setLatitude(location.getLatitude());
                locationModel.setLongitude(location.getLongitude());
                locationModel.setProvince(location.getProvince());
                locationModel.setCityName(location.getCity());
                locationModel.setCitycode(location.getCityCode());
                locationModel.setDistrict(location.getDistrict());
                locationModel.setAddress(location.getAddrStr());
//                stopLocation();
                if(locationSuccessListener!=null)
                    locationSuccessListener.onLocationSuccess(locationModel);
            }
        }
    };
  public   interface LocationSuccessListener{
        public void onLocationSuccess(LocationModel locationModel);
    }

    public class LocationModel{
        private double Latitude;
        private double Longitude;
        private String province;
        private String cityName;
        private String citycode;
        private String district;
        private String address;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }
}
