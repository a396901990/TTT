package com.dean.travltotibet.model;

import android.graphics.drawable.Drawable;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

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

    public static Drawable getTypeImageSrcWithColor(String type, int color) {
        Drawable src = null;
        if (type.equals(BIKE)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_directions_bike, color);
        } else if (type.equals(HIKE)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_directions_walk, color);
        } else if (type.equals(MOTO)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_motorcycle, color);
        } else if (type.equals(CAR)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_directions_car, color);
        }
        return src;
    }

    public static Drawable getGoogleIconDrawable(final IIcon icon, int color) {
        color = TTTApplication.getMyColor(color);
        return new IconicsDrawable(TTTApplication.getContext(), icon).color(color).sizeDp(18);
    }

}
