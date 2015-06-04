package com.daen.google.runnable;

import com.daen.google.Geocode;
import com.daen.google.GoogleMapAPIUtil;
import com.daen.google.ParseJson;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class DetailsInfoRunnable implements Runnable {

    private ArrayList<Geocode> mGeocodes;

    private FetchCallback mCallbak;

    public DetailsInfoRunnable(ArrayList<Geocode> geocodes, FetchCallback callbak) {
        this.mGeocodes = geocodes;
        this.mCallbak = callbak;
    }


    public static interface FetchCallback {
        public void fetchSuccess(Geocode geocode);

        public void fetchFail();
    }

    @Override
    public void run() {

        for (Geocode g : mGeocodes) {
            getDetailInfo(g);
        }
    }

    public void getDetailInfo(final Geocode geocode) {
        String geocodeUrl = GoogleMapAPIUtil.getDeocodeUrl(geocode.getName());
        DownloadRunnable geocodeRunnable = new DownloadRunnable(geocodeUrl, new DownloadRunnable.DownloadCallback() {
            @Override
            public void downloadSuccess(String result) {
                try {
                    // fetch geocode info to geocode
                    ParseJson.parseGeocode(result, geocode);

                    String elevationURL = GoogleMapAPIUtil.getElevationLocationUrl(geocode.getLatitude(), geocode.getLongitude());
                    DownloadRunnable evleationRunable = new DownloadRunnable(elevationURL, new DownloadRunnable.DownloadCallback() {
                        @Override
                        public void downloadSuccess(String result) {
                            try {
                                // fetch elevation info to geocode
                                ParseJson.parseElevation(result, geocode);

                                // invoke callback
                                mCallbak.fetchSuccess(geocode);
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
                    Thread t = new Thread(evleationRunable);
                    t.start();

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
