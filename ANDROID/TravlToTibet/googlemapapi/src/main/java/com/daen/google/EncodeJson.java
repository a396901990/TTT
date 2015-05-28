package com.daen.google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 95 on 2015/5/28.
 */
public class EncodeJson {

    public static final String FILE_PATH = "C:/Users/95/Desktop/result.txt";
    public static void main(String[] args) throws Exception {

        String a = readFile(FILE_PATH);
        //System.out.println(a);
        JSONObject jsonObject = new JSONObject(a);

        StringBuilder builder = new StringBuilder();

        JSONArray routes = jsonObject.getJSONArray("routes");
        JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
        JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
        for(int i=0;i<steps.length();i++){
            JSONObject polyline = steps.getJSONObject(i).getJSONObject("polyline");
            builder.append(polyline.getString("points"));
        }
        System.out.print(builder.toString());
    }

    public static String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
