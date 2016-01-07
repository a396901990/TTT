package com.daen.google.util;

import com.daen.google.module.Direction;
import com.daen.google.module.Geocode;
import com.daen.google.module.GeocodesJson;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/28.
 */
public class ParseJson {

    public static final String FILE_PATH = "C:/Users/95/Desktop/result.txt";

    public static final String OUTPUT_FILE_PATH = "C:/Users/95/Desktop/outputresult.txt";

    public static final String OUTPUT_FILE_PATH_MAC = "/Users/DeanGuo/Desktop/result.txt";

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
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
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

    public static boolean writefile(String path, String content) {
        try {
            OutputStream out = new FileOutputStream(path);
            out.write(content.getBytes());
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * parse geocodes to json and saved into local file
     */
    public static void parseToFile(ArrayList<Geocode> geocodes) {
        Gson gson = new Gson();
        GeocodesJson gj = new GeocodesJson();
        gj.setGeocodes(geocodes);
        String result = gson.toJson(gj, GeocodesJson.class);
        writefile(OUTPUT_FILE_PATH_MAC, result);
    }

    /**
     * 解析Directions返回值并为Geocode赋值
     */
    public static String parseDirection(String result, Geocode geocode) throws JSONException {
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

    /**
     * 解析Directions返回值并为Geocode赋值
     */
    public static Direction parseDirections(String result) throws JSONException {

        JSONObject jsonObject = new JSONObject(result);

        JSONArray routes = jsonObject.getJSONArray("routes");
        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
        JSONObject distance = legs.getJSONObject(0).getJSONObject("distance");
        String distance_value = distance.getString("value");

        JSONObject overview_polyline = routes.getJSONObject(0).getJSONObject("overview_polyline");
        String points = overview_polyline.getString("points");

        String summary = routes.getJSONObject(0).getString("summary");

        return new Direction(distance_value, points, summary);
    }

    /**
     * 解析Geocode返回值并为Geocode赋值
     */
    public static Geocode parseGeocode(String result, Geocode geocode) throws JSONException {

        JSONObject jsonObject = new JSONObject(result);

        // results(address_components, formatted_address, geometry)
        JSONArray results = jsonObject.getJSONArray("results");

        // address_components
        JSONArray address_components = results.getJSONObject(0).getJSONArray("address_components");

        // formatted_address
        String address = results.getJSONObject(0).getString("formatted_address");

        // geometry
        JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        Double lat = location.getDouble("lat");
        Double lng = location.getDouble("lng");

        geocode.setAddress(address);
        geocode.setLatitude(lat);
        geocode.setLongitude(lng);
        return geocode;
    }

    /**
     * 解析Elevation返回值并为Geocode赋值
     */
    public static Geocode parseElevation(String result, Geocode geocode) throws JSONException {
        //result = readFile("C:/Users/95/Desktop/elevation.txt");

        JSONObject jsonObject = new JSONObject(result);

        JSONArray results = jsonObject.getJSONArray("results");
        System.out.print(geocode.getName());
        Double elevation = results.getJSONObject(0).getDouble("elevation");
        geocode.setElevation(elevation);
        return geocode;
    }

    /**
     * 解析Elevation返回值并为Geocode赋值
     */
    public static ArrayList<Geocode> parseElevationPath(String result, Geocode origin, Geocode destination) throws JSONException {
        //result = readFile("C:/Users/95/Desktop/elevation.txt");

        JSONObject jsonObject = new JSONObject(result);

        JSONArray results = jsonObject.getJSONArray("results");
        ArrayList<Geocode> geocodes = new ArrayList<Geocode>();

        // 计算每段小距离
        double mileage_length = destination.getMileage() - origin.getMileage();
        double mileage_unit = mileage_length / (results.length() + 1);

        double distance = origin.getDistance() / (results.length() + 1);

        for (int i = 0; i < results.length(); i++) {
            double elevation = results.getJSONObject(i).getDouble("elevation");
            JSONObject location = results.getJSONObject(i).getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            String name = origin.getName() + "_" + destination.getName() + "_" + i;

            double mileage = origin.getMileage() + (i + 1) * mileage_unit;
            if (mileage >= destination.getMileage()) {
                mileage = destination.getMileage();
            }

            geocodes.add(new Geocode(name, elevation, mileage, lat, lng, distance, "", "PATH"));
        }
        origin.setDistance(distance);
        return geocodes;
    }

    /**
     * 获取当前路径
     */
    public String getCurrentPath() {
        //取得根目录路径
        String rootPath = getClass().getResource("/").getFile().toString();
        //当前目录路径
        String currentPath1 = getClass().getResource(".").getFile().toString();
        String currentPath2 = getClass().getResource("").getFile().toString();
        //当前目录的上级目录路径
        String parentPath = getClass().getResource("../").getFile().toString();

        return rootPath;
    }
}
