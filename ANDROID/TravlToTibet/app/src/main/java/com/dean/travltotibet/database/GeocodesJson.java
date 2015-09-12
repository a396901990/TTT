package com.dean.travltotibet.database;

import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeOld;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class GeocodesJson {

    public ArrayList<GeocodeOld> geocodes;

    public ArrayList<GeocodeOld> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(ArrayList<GeocodeOld> geocodes) {
        this.geocodes = geocodes;
    }
}
