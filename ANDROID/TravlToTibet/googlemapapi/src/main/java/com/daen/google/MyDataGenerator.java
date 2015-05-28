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
        Geocode geocode = new Geocode();
        geocode.setName("Ò¶³ÇÏØ");

        geocodes.add(geocode);
        ParseJson.parseGeocode("", geocode);
        ParseJson.parseElevation("", geocode);
        //getDetail(geocode.getName());

    }
    public static final String FILE_PATH = "C:/Users/95/Desktop/result.txt";

    public static void resolveDetail() {
        String a = ParseJson.readFile(FILE_PATH);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject.toString();
    }


    public static void getDetail(String name) {
        HttpHelper httpHelper = new HttpHelper();
        String url = GoogleMapAPIUtil.getDeocodeUrl(name);
        try {
            String result = httpHelper.sendPost(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
