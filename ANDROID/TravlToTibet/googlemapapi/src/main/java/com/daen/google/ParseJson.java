package com.daen.google;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by 95 on 2015/5/28.
 */
public class ParseJson {

    public static final String FILE_PATH = "C:/Users/95/Desktop/result.txt";

    public static void main(String[] args) throws Exception {

        String a = readFile(FILE_PATH);
        //System.out.println(a);
        JSONObject jsonObject = new JSONObject(a);

        StringBuilder builder = new StringBuilder();

        JSONArray routes = jsonObject.getJSONArray("routes");
        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
        JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
        for (int i = 0; i < steps.length(); i++) {
            JSONObject polyline = steps.getJSONObject(i).getJSONObject("polyline");
            builder.append(polyline.getString("points"));
        }
        System.out.print(builder.toString());
    }

    public static String readFile(String filename) {
        StringBuffer lines = new StringBuffer();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "gbk"));
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.append(line);
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.toString();
    }

    public static String parseDirections(String result) throws JSONException {
        result = readFile(FILE_PATH);
        //System.out.println(a);
        JSONObject jsonObject = new JSONObject(result);

        StringBuilder builder = new StringBuilder();

        JSONArray routes = jsonObject.getJSONArray("routes");
        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
        JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
        for (int i = 0; i < steps.length(); i++) {
            JSONObject polyline = steps.getJSONObject(i).getJSONObject("polyline");
            builder.append(polyline.getString("points"));
        }
        System.out.print(builder.toString());

        return builder.toString();
    }

    public static Geocode parseGeocode(String result, Geocode geocode) throws JSONException {
        //result = readFile("C:/Users/95/Desktop/geocode.txt");

        JSONObject jsonObject = new JSONObject(result);

        String name;
        String address = "";
        Double lat = 0.0;
        Double lng = 0.0;

        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            // address_components
            JSONArray address_components = results.getJSONObject(i).getJSONArray("address_components");
            JSONObject first_address = address_components.getJSONObject(0);

            // long_name
            name = first_address.getString("long_name");
            if (geocode.getName().equals(name)) {

                // formatted_address
                address = results.getJSONObject(i).getString("formatted_address");

                // geometry
                JSONObject geometry = results.getJSONObject(i).getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                lat = location.getDouble("lat");
                lng = location.getDouble("lng");
            }
        }

        geocode.setAddress(address);
        geocode.setLatitude(lat);
        geocode.setLongitude(lng);
        return geocode;
    }

    public static Geocode parseElevation(String result, Geocode geocode) throws JSONException {
        //result = readFile("C:/Users/95/Desktop/elevation.txt");

        JSONObject jsonObject = new JSONObject(result);

        JSONArray results = jsonObject.getJSONArray("results");
        Double elevation = results.getJSONObject(0).getDouble("elevation");
        geocode.setElevation(elevation);
        return geocode;
    }
}
