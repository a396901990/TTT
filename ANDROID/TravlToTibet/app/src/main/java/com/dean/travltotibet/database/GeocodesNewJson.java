package com.dean.travltotibet.database;

import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeOld;

import java.util.ArrayList;

/**
 * Created by Dean on 2015/5/29.
 */
public class GeocodesNewJson {

    public ArrayList<Geocode> geocodes;

    public ArrayList<Geocode> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(ArrayList<Geocode> geocodes) {
        this.geocodes = geocodes;
    }
}
