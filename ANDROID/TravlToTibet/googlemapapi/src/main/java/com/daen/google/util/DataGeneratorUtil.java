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
import java.util.Comparator;
import java.util.Iterator;

import sun.rmi.runtime.Log;

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
}
