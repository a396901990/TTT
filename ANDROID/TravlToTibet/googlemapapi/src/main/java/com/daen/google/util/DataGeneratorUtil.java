package com.daen.google.util;

import com.daen.google.module.Constants;
import com.daen.google.module.Direction;
import com.daen.google.module.Geocode;
import com.daen.google.module.GeocodesJson;
import com.daen.google.module.LatLng;
import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Dean on 2015/5/28.
 */
public final class DataGeneratorUtil {

    /**
     * 获取路径信息
     */
    private void getPathInfo(ArrayList<Geocode> geos) {
        PathRunnable pathRunnable = new PathRunnable(geos, new PathRunnable.FetchCallback() {

            @Override
            public void fetchSuccess(Geocode geocode) {
            }

            @Override
            public void fetchFinished(ArrayList<Geocode> geocodes) {
                onLoadingFinish(geocodes);
            }

            @Override
            public void fetchFail() {

            }
        });

        Thread t = new Thread(pathRunnable);
        t.start();
    }

    public void onLoadingFinish(ArrayList<Geocode> geocodes) {
        ArrayList<Geocode> mGeos = getAllData(geocodes);
        removeMultiple(mGeos);
        logData(mGeos);
        ParseJson.parseToFile(mGeos);
    }


    /**
     * 获取数据
     */
    public void getData(ArrayList<Geocode> geocodes) {

        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

            }

            @Override
            public void fetchFinish(ArrayList<Geocode> geos) {
                Collections.sort(geos, Geocode.MileageComparator);

                getPathInfo(geos);
            }

            @Override
            public void fetchFail() {

            }
        });

        Thread t = new Thread(detailsInfoRunnable);
        t.start();
    }

    /**
     * 获取Lat,Lng数据
     */
    public void getLLData(ArrayList<Geocode> geocodes) {

        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

            }

            @Override
            public void fetchFinish(ArrayList<Geocode> geos) {
                Collections.sort(geos, Geocode.MileageComparator);
                onLoadingFinish(geos);
            }

            @Override
            public void fetchFail() {

            }
        });

        Thread t = new Thread(detailsInfoRunnable);
        t.start();
    }

    /**
     * 设置Geocode顺序（有点问题，用不了现在）
     */
    private void setGeocodeOrder(ArrayList<Geocode> geocodes) {
        for (int i = 0; i < geocodes.size(); i++) {
            Geocode geocode = geocodes.get(i);
            geocode.setMileage(i);
        }
    }

    /**
     * 打印结果到控制台
     */
    private void logData(ArrayList<Geocode> mGeos) {
        Iterator<Geocode> it = mGeos.iterator();
        while (it.hasNext()) {
            Geocode geo = it.next();
            System.out.println(geo.getName() + ":              高度：" + geo.getElevation() + "                   距离" + geo.getDistance());
        }
    }

    /**
     * 去出重复数据
     */
    private void removeMultiple(ArrayList<Geocode> geocodes) {
        Iterator<Geocode> it = geocodes.iterator();
        while (it.hasNext()) {
            Geocode geo = it.next();

            if (geo.getDistance() == 0 && !it.hasNext()) {
                it.remove();
            }
        }
        Collections.sort(geocodes, Geocode.MileageComparator);
    }

    /**
     * 新数据加文件数据排序后一起返回
     */
    private ArrayList<Geocode> getAllData(ArrayList<Geocode> geocodes) {
        String json_result = ParseJson.readFile(ParseJson.OUTPUT_FILE_PATH_MAC);
        if (json_result == null || json_result.equals("")) {
            return geocodes;
        }
        Gson gson = new Gson();
        GeocodesJson geocodesJson = gson.fromJson(json_result, GeocodesJson.class);

        if (geocodesJson.getGeocodes().size() != 0) {
            geocodes.addAll(geocodesJson.getGeocodes());
        }

        return geocodes;
    }

    public static double getDistance(LatLng start,LatLng end){
        double lat1 = (Math.PI/180)*start.getLatitude();
        double lat2 = (Math.PI/180)*end.getLatitude();

        double lon1 = (Math.PI/180)*start.getLongitude();
        double lon2 = (Math.PI/180)*end.getLongitude();

//      double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
//      double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
//      double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
//      double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

        return d;
    }

    public static ArrayList<Geocode> getNewGeocodes(ArrayList<Geocode> oldGeos, int kilos) {
        int points = kilos * 1000 / 1000;
        final int pointsGap = oldGeos.size() / points;

        ArrayList<Geocode> newGeos = new ArrayList<>();
        for (int i = 0; i < oldGeos.size(); i += pointsGap) {
            Geocode geocode = oldGeos.get(i);
            double distance = getDistance(oldGeos, i, pointsGap);
            geocode.setDistance(distance);
            if (i == 0) {
                geocode.setTypes(Constants.CITY);
            }
            newGeos.add(geocode);
        }

        // last geocode
        Geocode lastGeo = oldGeos.get(oldGeos.size()-1);
        lastGeo.setDistance(0);
        lastGeo.setTypes(Constants.CITY);
        newGeos.add(lastGeo);
        return newGeos;
    }

    public static double getDistance(ArrayList<Geocode> geos, int start, int pointGap) {
        double distance = 0;
        for (int i = start; i < start + pointGap; i ++) {
            if (i < geos.size()) {
                Geocode geocode = geos.get(i);
                distance += geocode.getDistance();
            }
        }

        return distance;
    }
}
