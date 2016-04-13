package com.daen.google.util;

import com.daen.google.module.Constants;
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

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
 * Created by Dean on 2015/5/28.
 */
public final class KMLGeneratorUtil {

    public static ArrayList<Geocode> getNewGeocodesOld(ArrayList<Geocode> oldGeos, int kilos) {
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
        Geocode lastGeo = oldGeos.get(oldGeos.size() - 1);
        lastGeo.setDistance(0);
        lastGeo.setTypes(Constants.CITY);
        newGeos.add(lastGeo);
        return newGeos;
    }

    public static double getDistance(LatLng start, LatLng end) {
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.getLatitude();

        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.getLongitude();

//      double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
//      double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
//      double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
//      double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        return d;
    }

    public static ArrayList<Geocode> getNewGeocodes(ArrayList<Geocode> oldGeos, int kilos) {

        ArrayList<Geocode> newGeos = new ArrayList<>();

        int DIVIDE_PART = 5; // 每一份取三个点，最高，中间，最低
        int points = kilos * 1000 / 1000;
        final int pointsGap = oldGeos.size() / points * DIVIDE_PART; // 算出要多少份，每份取3个点，所以这步要除3，这样才能保证分割取点正确


        // 不取第一个点，不取最后一个点，最后统一添加
        for (int i = 1; i < oldGeos.size() - 1; i += pointsGap) {
            // 每一份，从i开始，到i+pointsGap，中的点取3个
            // 从根据海拔排序的单段geo中找出最低，最高，和中间的3个点
            ArrayList<Geocode> threeGeo = getThreeGeo(oldGeos, i, pointsGap);
            for (Geocode g : threeGeo) {
                newGeos.add(g);
            }
        }

        return newGeos;
    }

    public static ArrayList<Geocode> getGeoDistance(ArrayList<Geocode> newGeo, ArrayList<Geocode> oldGeo) {

        ArrayList<Geocode> reorder = newGeo;

        // 循环每一个新geo
        for (int i = 0; i < reorder.size(); i++) {
            Geocode geocode = reorder.get(i);
            if (i + 1 < reorder.size()) {
                Geocode nextGeocode = reorder.get(i + 1);
                double distance = getDistance(oldGeo, geocode, nextGeocode);
                geocode.setDistance(distance);
            }
        }

        return reorder;
    }

    public static double getDistance(ArrayList<Geocode> geos, int start, int pointGap) {
        double distance = 0;
        for (int i = start; i < start + pointGap; i++) {
            if (i < geos.size()) {
                Geocode geocode = geos.get(i);
                distance += geocode.getDistance();
            }
        }

        return distance;
    }

    public static double getDistance(ArrayList<Geocode> geos, Geocode firstGeo, Geocode nextGeo) {
        double distance = 0;

        int start = firstGeo.getMileage() > nextGeo.getMileage() ? (int) nextGeo.getMileage() : (int) firstGeo.getMileage();;
        int end = firstGeo.getMileage() < nextGeo.getMileage() ? (int) nextGeo.getMileage() : (int) firstGeo.getMileage();;
        for (int i = start; i < end; i++) {
            if ((i) < geos.size()) {
                Geocode geocode = geos.get(i);
                distance += geocode.getDistance();
            }
        }

        return distance;
    }

    public static ArrayList<Geocode> getThreeGeo(ArrayList<Geocode> geos, int start, int pointGap) {
        ArrayList<Geocode> newOrderByHeight = new ArrayList<>();
        for (int i = start; i < start + pointGap; i++) {
            if (i < geos.size()) {
                Geocode geocode = geos.get(i);
                newOrderByHeight.add(geocode);
            }
        }

        Collections.sort(newOrderByHeight, new Comparator<Geocode>() {
            @Override
            public int compare(Geocode o1, Geocode o2) {
                if (o1.getElevation() > o2.getElevation()) {
                    return 1;
                } else if (o1.getElevation() < o2.getElevation()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        // 从根据海拔排序的单段geo中找出最低，最高，和中间的N个点
        ArrayList<Geocode> afterDivide = getDividePoint(newOrderByHeight, 4);

        return afterDivide;
    }

    private static ArrayList<Geocode> getDividePoint(ArrayList<Geocode> newOrderByHeight, int part) {
        ArrayList<Geocode> dividePoint = new ArrayList<>();

        // 第一个
        Geocode lowestGeo = newOrderByHeight.get(0);
        dividePoint.add(lowestGeo);

        // 最后一个
        Geocode heightestGeo = newOrderByHeight.get(newOrderByHeight.size() - 1);
        dividePoint.add(heightestGeo);

        // 中间的i-2个点
        int divideGap = (newOrderByHeight.size() - 2) / part;
        for (int i = divideGap; i < newOrderByHeight.size() - divideGap; i += divideGap) {
            dividePoint.add(newOrderByHeight.get(i));
        }

        return dividePoint;
    }

    public static ArrayList<Geocode> reOrder(ArrayList<Geocode> geocodes, final boolean isForword) {

        ArrayList<Geocode> reOrderGeos = geocodes;

        Collections.sort(reOrderGeos, new Comparator<Geocode>() {
            @Override
            public int compare(Geocode o1, Geocode o2) {
                if (o1.getMileage() > o2.getMileage()) {
                    return isForword ? 1 : -1;
                } else if (o1.getMileage() < o2.getMileage()) {
                    return isForword ? -1 : 1;
                } else {
                    return 0;
                }
            }
        });


        return reOrderGeos;
    }

    public static ArrayList<Geocode> getDistanceForGeo(ArrayList<Geocode> geocodes) {

        ArrayList<Geocode> reOrderGeos = geocodes;

        for (int i=0; i<reOrderGeos.size();i++) {

            Geocode firstGeo = reOrderGeos.get(i);
            // distance
            double distance = 0;
            if (i < reOrderGeos.size() - 1) {
                Geocode nextGeo = reOrderGeos.get(i + 1);

                LatLng firstLatlng = new LatLng(firstGeo.getLatitude(), firstGeo.getLongitude());
                LatLng secondLatlng = new LatLng(nextGeo.getLatitude(), nextGeo.getLongitude());
                distance = KMLGeneratorUtil.getDistance(firstLatlng, secondLatlng);
            }
            firstGeo.setDistance(distance);
        }

        return reOrderGeos;
    }

    public static ArrayList<Geocode> addPoint(ArrayList<Geocode> newGeos, ArrayList<Geocode> oldGeos, ArrayList<String> point) {
        ArrayList<Geocode> tempGeo = newGeos;

        for (String pointName : point) {
            // 如果不包含这个点，要加上这个点
            if (!hasPoint(tempGeo, pointName)) {
                Geocode addGeo = getPoint(oldGeos, pointName);
                if (addGeo != null) {
                    addGeo.setTypes(Constants.CITY);
                    newGeos.add(addGeo);
                }
            }
        }

        return newGeos;
    }

    private static boolean hasPoint(ArrayList<Geocode> newGeos, String pointName) {
        for (Geocode geocode : newGeos) {
            if (pointName.equals(geocode.getName())) {
                return true;
            }
        }
        return false;
    }

    private static Geocode getPoint(ArrayList<Geocode> oldGeos, String pointName) {
        for (Geocode geocode : oldGeos) {
            if (pointName.equals(geocode.getName())) {
                return geocode;
            }
        }
        return null;
    }

    public static ArrayList<Geocode> getReversGeo(ArrayList<Geocode> newGeos) {

        // 设置反向distance,用setMilestone暂存
        for (int i = 0; i < newGeos.size(); i++) {
            Geocode geocode = newGeos.get(i);
            double r_distance;
            if (i == 0) {
                r_distance = 0;
            } else {
                r_distance = newGeos.get(i - 1).getDistance();
            }
            geocode.setMilestone(r_distance);
        }

        // 反转元素
        newGeos = reOrder(newGeos, false);

        // 调换正反distance
        for (int i = 0; i < newGeos.size(); i++) {
            Geocode geocode = newGeos.get(i);
            geocode.setDistance(geocode.getMilestone());
        }
        return newGeos;
    }
}