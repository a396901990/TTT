package com.dean.travltotibet.model;

import android.graphics.drawable.Drawable;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;

/**
 * Created by DeanGuo on 9/26/15.
 */
public class TravelType {

    public final static String BIKE = "BIKE";
    public final static String HIKE = "HIKE";
    public final static String MOTO = "MOTO";
    public final static String CAR = "CAR";


    public static Drawable getBlueTypeImageSrc(String type) {
        Drawable src = null;
        if (type.equals(BIKE)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_bike_blue);
        } else if (type.equals(HIKE)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_hike_blue);
        } else if (type.equals(MOTO)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_moto_blue);
        } else if (type.equals(CAR)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_car_blue);
        }
        return src;
    }

    public static Drawable getWhiteTypeImageSrc(String type) {
        Drawable src = null;
        if (type.equals(BIKE)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_bike_white);
        } else if (type.equals(HIKE)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_hike_white);
        } else if (type.equals(MOTO)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_moto_white);
        } else if (type.equals(CAR)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_car_white);
        }
        return src;
    }

}
