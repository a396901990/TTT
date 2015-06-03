package com.daen.google;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/5/29.
 */
public class DetailsInfoRunnable implements Runnable {

    private ArrayList<Geocode> mGeocodes;

    private FetchCallback mCallbak;

    private String result;

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
        String deocodeUrl = GoogleMapAPIUtil.getDeocodeUrl(geocode.getName());
        DownloadRunnable deocodeRunable = new DownloadRunnable(deocodeUrl, new DownloadRunnable.DownloadCallback() {
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

        Thread t = new Thread(deocodeRunable);
        t.start();
    }

    public ArrayList<Geocode> getmGeocodes() {
        return mGeocodes;
    }

    public void setmGeocodes(ArrayList<Geocode> mGeocodes) {
        this.mGeocodes = mGeocodes;
    }

    public FetchCallback getCallbak() {
        return mCallbak;
    }

    public void setCallbak(FetchCallback mCallbak) {
        this.mCallbak = mCallbak;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
