package com.daen.google.runnable;

import com.daen.google.Direction;
import com.daen.google.Geocode;
import com.daen.google.GoogleMapAPIUtil;
import com.daen.google.ParseJson;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/6/5.
 */
public class PathRunnable implements Runnable {

    private ArrayList<Geocode> mGeocodes;

    private FetchCallback mCallback;

    private int count;

    private ArrayList<Geocode> tempGeocodes;

    public PathRunnable(ArrayList<Geocode> geocodes, FetchCallback callbak) {
        this.mGeocodes = geocodes;
        this.mCallback = callbak;
        this.tempGeocodes = geocodes;
        this.count = geocodes.size();
    }


    public static interface FetchCallback {
        public void fetchSuccess(Geocode geocode);

        public void fetchFinished(ArrayList<Geocode> geocodes);

        public void fetchFail();

    }

    @Override
    public void run() {

        for (int i = 0; i < mGeocodes.size(); i++) {

            if (i + 1 < mGeocodes.size()) {
                getPathInfo(mGeocodes.get(i), mGeocodes.get(i + 1), i);
            }
        }
    }

    public void getPathInfo(final Geocode origin, final Geocode destination, final int i) {
        //String directionsUrl = GoogleMapAPIUtil.getDirectionsUrl(origin.getAddress(), destination.getAddress());
        String directionsUrl;
        if ((origin.getBelong() != null && origin.getBelong().length() > 0) || (destination.getBelong() != null && destination.getBelong().length() > 0)) {
            directionsUrl = GoogleMapAPIUtil.getDirectionsUrl(origin.getAddress(), destination.getAddress());
        } else {
            directionsUrl = GoogleMapAPIUtil.getDirectionsUrl(origin.getName(), destination.getName());
        }
        //directionsUrl = GoogleMapAPIUtil.getDirectionsUrl(origin.getName(), destination.getName());
        DownloadRunnable directionsRunnable = new DownloadRunnable(directionsUrl, new DownloadRunnable.DownloadCallback() {

            @Override
            public void downloadSuccess(String result) {
                try {
                    // fetch points between two geocode
                    Direction direction = ParseJson.parseDirections(result);
                    String points = direction.getPoints();
                    String distance = direction.getDistance();
                    String summary = direction.getSummary();

                    origin.setRoad(summary);
                    origin.setDistance(Double.parseDouble(distance)/1000);
                    int samples = Integer.parseInt(distance) / Direction.DIRECTION_UNIT;
                    if (samples < 2) {
                        samples = 2;
                    }
                    String elevationURL = GoogleMapAPIUtil.getElevationPathUrl(points, samples);
                    DownloadRunnable elevationRunnable = new DownloadRunnable(elevationURL, new DownloadRunnable.DownloadCallback() {
                        @Override
                        public void downloadSuccess(String result) {
                            try {
                                // fetch elevation info to geocode
                                ArrayList<Geocode> geocodes = ParseJson.parseElevationPath(result, origin, destination);

                                count--;
                                tempGeocodes.addAll(geocodes);

                                // do not need to calc last location
                                if (count == 1) {
                                    // invoke callback
                                    mCallback.fetchFinished(tempGeocodes);
                                }
                            } catch (JSONException e) {
                                mCallback.fetchFail();
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadFail() {

            }
        });

        Thread t = new Thread(directionsRunnable);
        t.start();
    }

}
