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
    public final static String OTHER = "OTHER";

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
        } else if (type.equals(OTHER)) {
            src = null;
        }
        return src;
    }

    public static Drawable getActionBarImageSrc(String type) {
        Drawable src = null;
        if (type.equals(BIKE)) {
            src = getActionbarIconDrawable(GoogleMaterial.Icon.gmd_directions_bike);
        } else if (type.equals(HIKE)) {
            src = getActionbarIconDrawable(GoogleMaterial.Icon.gmd_directions_walk);
        } else if (type.equals(MOTO)) {
            src = getActionbarIconDrawable(GoogleMaterial.Icon.gmd_motorcycle);
        } else if (type.equals(CAR)) {
            src = getActionbarIconDrawable(GoogleMaterial.Icon.gmd_directions_car);
        }
        return src;
    }

    public static String getTravelText(String type) {
        String typeText = null;
        if (type.equals(BIKE)) {
            typeText = TTTApplication.getMyResources().getString(R.string.travel_type_bike);
        } else if (type.equals(HIKE)) {
            typeText = TTTApplication.getMyResources().getString(R.string.travel_type_hike);
        } else if (type.equals(MOTO)) {
            typeText = TTTApplication.getMyResources().getString(R.string.travel_type_moto);
        } else if (type.equals(CAR)) {
            typeText = TTTApplication.getMyResources().getString(R.string.travel_type_car);
        } else if (type.equals(OTHER)) {
            typeText = TTTApplication.getMyResources().getString(R.string.travel_type_other);
        }
        return typeText;
    }

    public static Drawable getGoogleIconDrawable(final IIcon icon, int color) {
        color = TTTApplication.getMyColor(color);
        return new IconicsDrawable(TTTApplication.getContext(), icon).color(color).sizeDp(18);
    }


    public static Drawable getActionbarIconDrawable(final IIcon icon) {
        int color = TTTApplication.getMyColor(R.color.white);
        return new IconicsDrawable(TTTApplication.getContext(), icon).color(color).sizeDp(IconicsDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP);
    }

}
