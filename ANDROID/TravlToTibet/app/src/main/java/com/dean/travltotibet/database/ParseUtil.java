package com.dean.travltotibet.database;

import android.content.Context;

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

}
