package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {

    public static void main(String[] args) throws Exception {

        final ArrayList<Geocode> geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("叶城县", 0, "CITY"));
        geocodes.add(new Geocode("柯克亚乡", 62, "VILLAGE"));
        geocodes.add(new Geocode("普萨村", 72, "VILLAGE"));
        geocodes.add(new Geocode("阿克美其特村", 100, "叶城县", "VILLAGE"));
        geocodes.add(new Geocode("库地", 160, "TOWN"));
        geocodes.add(new Geocode("大红柳滩", 487, "TOWN"));

        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

            }

            @Override
            public void fetchFinish(ArrayList<Geocode> geos) {
                Collections.sort(geos, Geocode.MileageComparator);
                //ParseJson.parseToFile(geos);

                PathRunnable pathRunnable = new PathRunnable(geos, new PathRunnable.FetchCallback() {

                    @Override
                    public void fetchSuccess(Geocode geocode) {
                    }

                    @Override
                    public void fetchFinished(ArrayList<Geocode> geocodes) {
                        Collections.sort(geocodes, Geocode.MileageComparator);
                        ParseJson.parseToFile(geocodes);
                    }

                    @Override
                    public void fetchFail() {

                    }
                });

                Thread t = new Thread(pathRunnable);
                t.start();
            }

            @Override
            public void fetchFail() {

            }
        }
    );

    Thread t = new Thread(detailsInfoRunnable);
    t.start();
}
}
