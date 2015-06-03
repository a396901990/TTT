package com.daen.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {

    public static void main(String[] args) throws Exception {

        final ArrayList<Geocode> geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("叶城县", 0));
        geocodes.add(new Geocode("柯克亚乡", 62));
        geocodes.add(new Geocode("普萨村", 72));
        geocodes.add(new Geocode("阿克美其特村", 100, "叶城县"));
        geocodes.add(new Geocode("库地", 160));
        geocodes.add(new Geocode("大红柳滩", 487));

        final ArrayList<Geocode> geos = new ArrayList<Geocode>();
        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

                geos.add(geocode);
                if (geos.size() == geocodes.size()) {
                    Collections.sort(geos, Geocode.MileageComparator);
                    ParseJson.parseToFile(geos);
                    //System.out.print(geos);
                }
            }

            @Override
            public void fetchFail() {

            }
        });

        Thread t = new Thread(detailsInfoRunnable);
        t.start();
    }
}
