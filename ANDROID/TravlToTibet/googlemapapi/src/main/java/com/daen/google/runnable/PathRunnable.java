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

    private FetchCallback mCallbak;

    public PathRunnable(ArrayList<Geocode> geocodes, FetchCallback callbak) {
        this.mGeocodes = geocodes;
        this.mCallbak = callbak;
    }


    public static interface FetchCallback {
        public void fetchSuccess(ArrayList<Geocode> geocodes);

        public void fetchFinished();

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
        String directionsUrl = GoogleMapAPIUtil.getDirectionsUrl(origin.getName(), destination.getName());
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
                    int samples = Integer.parseInt(distance) / Direction.DIRECTION_UNIT;
                    if (samples < 2) {
                        samples = 2;
                    }
                    String elevationURL = GoogleMapAPIUtil.getElevationPathUrl(points, samples);
                    DownloadRunnable evleationRunnable = new DownloadRunnable(elevationURL, new DownloadRunnable.DownloadCallback() {
                        @Override
                        public void downloadSuccess(String result) {
                            try {
                                // fetch elevation info to geocode
                                ArrayList<Geocode> geocodes = ParseJson.parseElevationPath(result, origin, destination);

                                // invoke callback
                                mCallbak.fetchSuccess(geocodes);
                                mCallbak.fetchFinished();
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
                    Thread t = new Thread(evleationRunnable);
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
