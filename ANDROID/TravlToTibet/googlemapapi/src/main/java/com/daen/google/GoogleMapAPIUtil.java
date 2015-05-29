package com.daen.google;

import java.util.Locale;

/**
 * Created by Dean on 2015/5/28.
 */
public final class GoogleMapAPIUtil {

    public static final String KEY = "AIzaSyAoEvnY8lEliDQ5JWprEQ16OBFZ7SheZ-Q";

    public static final String LANGUAGE = "zh-CN";

    public static final String URL_PREFIX = "https://maps.googleapis.com/maps/api/";

    public static final String URL_POSTFIX = "&sensor=false&key=" + KEY + "&language=" + LANGUAGE;

    /**
     * doc https://developers.google.com/maps/documentation/elevation/?hl=zh-cN#api_key
     * sample https://maps.googleapis.com/maps/api/elevation/json?path=enc:cngfFonzwMpsg&samples=5&sensor=false
     */
    public static final String ELEVATION_PATH_URL = URL_PREFIX + "elevation/json?path=enc:%s&samples=%s" + URL_POSTFIX;

    /**
     * doc https://developers.google.com/maps/documentation/elevation/?hl=zh-cN#api_key
     * sample https://maps.googleapis.com/maps/api/elevation/json?path=36.578581,-118.291994|36.23998,-116.83171&samples=5
     */
    public static final String ELEVATION_LOCATION_URL = URL_PREFIX + "elevation/json?locations=%s,%s" + URL_POSTFIX;

    /**
     * doc https://developers.google.com/maps/documentation/directions/?hl=zh-cN#Steps
     * sample https://maps.googleapis.com/maps/api/directions/json?origin=叶城&destination=拉萨
     */
    public static final String DIRECTIONS_URL = URL_PREFIX + "directions/json?origin=%s&destination=%s&avoid=highways&mode=driving" + URL_POSTFIX;

    /**
     * doc https://developers.google.com/maps/documentation/geocoding/
     * sample http://maps.googleapis.com/maps/api/geocode/json?latlng=37.8855098,77.4718697&sensor=false
     */
    public static final String GEOCODE_URL = URL_PREFIX + "geocode/json?latlng=%s,%s" + URL_POSTFIX;


    /**
     * doc https://developers.google.com/maps/documentation/geocoding/
     * sample https://maps.googleapis.com/maps/api/geocode/json?address=叶城
     */
    public static final String GEOCODE_ADDRESS_URL = URL_PREFIX + "geocode/json?address=%s" + URL_POSTFIX;

    public static String getElevationPathUrl(String path, int samples) {
        String url = String.format(Locale.CHINA, ELEVATION_PATH_URL, path, samples);
        return url;
    }

    public static String getElevationLocationUrl(double latitude, double longitude) {
        String url = String.format(Locale.CHINA, ELEVATION_LOCATION_URL, latitude, longitude);
        return url;
    }

    public static String getDirectionsUrl(String origin, String destination) {
        String url = String.format(Locale.CHINA, DIRECTIONS_URL, origin, destination);
        return url;
    }

    public static String getDeocodeUrl(double latitude, double longitude) {
        String url = String.format(Locale.CHINA, GEOCODE_URL, latitude, longitude);
        return url;
    }

    public static String getDeocodeUrl(String address) {
        String url = String.format(Locale.CHINA, GEOCODE_ADDRESS_URL, address);
        return url;
    }

}
