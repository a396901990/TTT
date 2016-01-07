package com.daen.google.runnable;

import com.daen.google.module.Geocode;
import com.daen.google.util.GoogleMapURLUtil;
import com.daen.google.util.ParseJson;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class DetailsInfoRunnable implements Runnable {

    private ArrayList<Geocode> mGeocodes;

    private FetchCallback mCallbak;

    private ArrayList<Geocode> tempGeocodes = new ArrayList<Geocode>();

    public DetailsInfoRunnable(ArrayList<Geocode> geocodes, FetchCallback callbak) {
        this.mGeocodes = geocodes;
        this.mCallbak = callbak;
    }


    public static interface FetchCallback {
        public void fetchSuccess(Geocode tempGeocodes);

        public void fetchFinish(ArrayList<Geocode> tempGeocodes);

        public void fetchFail();
    }

    @Override
    public void run() {

        for (Geocode g : mGeocodes) {
            getDetailInfo(g);
        }
    }

    /**
     * 获取Geocode详细信息（address，lat，lng, elevation）
     * @param geocode
     */
    public void getDetailInfo(final Geocode geocode) {

        double geocodeLat = geocode.getLatitude();
        double geocodeLng = geocode.getLongitude();
        if (geocodeLat != 0 && geocodeLng != 0) {
            getElevationInfo(geocode);
        } else {
            getGeocodeInfo(geocode);
        }

    }

    /**
     * 获取海拔信息（Elevation）
     * @param geocode
     */
    public void getElevationInfo (final Geocode geocode) {
        // 获取Elevation信息
        String elevationURL = GoogleMapURLUtil.getElevationLocationUrl(geocode.getLatitude(), geocode.getLongitude());
        DownloadRunnable elevationRunnable = new DownloadRunnable(elevationURL, new DownloadRunnable.DownloadCallback() {
            @Override
            public void downloadSuccess(String result) {
                try {
                    // fetch elevation info to geocode
                    ParseJson.parseElevation(result, geocode);
                    tempGeocodes.add(geocode);

                    if (tempGeocodes.size() == mGeocodes.size()) {
                        // invoke callback
                        mCallbak.fetchFinish(tempGeocodes);
                    }
                } catch (JSONException e) {
                    mCallbak.fetchFail();
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadFail() {

            }
        });

        // started thread
        Thread t = new Thread(elevationRunnable);
        t.start();
    }

    /**
     * 获取geocode信息（address，lat，lng）
     * @param geocode
     */
    public void getGeocodeInfo(final Geocode geocode) {
        String geocodeUrl = GoogleMapURLUtil.getGeocodeUrl(geocode.getName());

        // 获取Geocode信息（address，lat，lnt）
        DownloadRunnable geocodeRunnable = new DownloadRunnable(geocodeUrl, new DownloadRunnable.DownloadCallback() {

            @Override
            public void downloadSuccess(String result) {
                try {
                    // fetch geocode info to geocode
                    ParseJson.parseGeocode(result, geocode);

                    // 获取Elevation信息
                    getElevationInfo(geocode);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadFail() {

            }
        });

        Thread t = new Thread(geocodeRunnable);
        t.start();
    }
}
