package com.dean.travltotibet.database;

import android.content.Context;
import android.os.Environment;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/28.
 */
public class ParseUtil {

    public static final String OUTPUT_FILE_PATH = "C:/Users/95/Desktop/outputresult.txt";

    public static String readFromRaw(Context mContext) {
        try {
            InputStream is = mContext.getResources().openRawResource(R.raw.data);
            String result = readInputStream(is);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInputStream(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is, "gbk");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public static void parseToFile(ArrayList<Geocode> geocodes) {
        Gson gson = new Gson();
        GeocodesJson gj = new GeocodesJson();
        gj.setGeocodes(geocodes);
        String result = gson.toJson(gj, GeocodesJson.class);
        writefile(OUTPUT_FILE_PATH, result);
    }


    public static boolean writefile(String path, String content) {
        try {

            String sd_default = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            String p = sd_default + "/output.txt";
            OutputStream out = new FileOutputStream(p);
            out.write(content.getBytes());
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}