package com.dean.travltotibet.model;

import android.graphics.drawable.Drawable;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeanGuo on 1/12/15.
 */
public final class AroundType {

    public final static String HOTEL = "H";  // 住宿
    public final static String FOOD = "F";  // 吃饭
    public final static String ATM = "A";  // ATM
    public final static String GAS_STATION = "G";  // 加油站
    public final static String CHECK_POINT = "C";  // 检查点
    public final static String SCENIC = "S";  // 双向景点
    public final static String SCENIC_F = "SF";  // 正向景点
    public final static String SCENIC_R = "SR";  // 反向景点
    public final static String CAMP_POINT = "CP";  // 扎营点

    private static final Map<String, String> TITLES = new HashMap<String, String>();

    static {
        TITLES.put(HOTEL, "住宿");
        TITLES.put(FOOD, "吃饭");
        TITLES.put(ATM, "ATM");
        TITLES.put(GAS_STATION, "加油站");
        TITLES.put(CHECK_POINT, "检查点");
        TITLES.put(SCENIC, "景点");
        TITLES.put(CAMP_POINT, "扎营点");
    }

    public static Drawable getAroundDrawableSrc(String type) {
        Drawable src = null;
        if (type.equals(HOTEL)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_local_hotel, R.color.white);
        } else if (type.equals(FOOD)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_local_dining, R.color.white);
        } else if (type.equals(ATM)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_local_atm, R.color.white);
        } else if (type.equals(GAS_STATION)) {
            src = getGoogleIconDrawable(GoogleMaterial.Icon.gmd_local_gas_station, R.color.white);
        } else if (type.equals(CHECK_POINT)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_check_point);
        } else if (type.equals(SCENIC)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_landscape);
        } else if (type.equals(CAMP_POINT)) {
            src = TTTApplication.getResourceUtil().getDrawableResourece(R.drawable.icon_camp);
        }
        return src;
    }

    public static String getAroundName(String type) {
        return TITLES.get(type);
    }

    public static Drawable getGoogleIconDrawable(final IIcon icon, int color) {
        color = TTTApplication.getMyColor(color);
        return new IconicsDrawable(TTTApplication.getContext(), icon).color(color).sizeDp(20);
    }

}
