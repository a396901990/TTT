package com.daen.google;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {

    public static void main(String[] args) throws Exception {

        ArrayList<Geocode> geocodes = new ArrayList<Geocode>();
        final Geocode geocode = new Geocode();
        geocode.setName("阿克美其特村");

        geocodes.add(geocode);

        String url = GoogleMapAPIUtil.getDeocodeUrl(geocode.getName());
        DownloadRunable deocodeRunable = new DownloadRunable(url, new DownloadRunable.DownloadCallback() {
            @Override
            public void downloadSuccess(String result) {
                try {
                    ParseJson.parseGeocode(result, geocode);

                    String url = GoogleMapAPIUtil.getElevationLocationUrl(geocode.getLatitude(), geocode.getLongitude());
                    DownloadRunable evleationRunable = new DownloadRunable(url, new DownloadRunable.DownloadCallback() {
                        @Override
                        public void downloadSuccess(String result) {
                            try {
                                ParseJson.parseElevation(result, geocode);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void downloadFaild() {

                        }
                    });

                    Thread t = new Thread(evleationRunable);
                    t.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void downloadFaild() {

            }
        });



        Thread t = new Thread(deocodeRunable);
        t.start();
    }

}
