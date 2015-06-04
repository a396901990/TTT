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

        final ArrayList<Geocode> geos = new ArrayList<Geocode>();
        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {


                geos.add(geocode);
                if (geos.size() == geocodes.size()) {
                    Collections.sort(geos, Geocode.MileageComparator);
                    //ParseJson.parseToFile(geos);

                    PathRunnable pathRunnable = new PathRunnable(geos, new PathRunnable.FetchCallback() {

                        int count = 0;
                        @Override
                        public void fetchSuccess(ArrayList<Geocode> geocodes) {
                            count ++;
                            for (Geocode g : geocodes) {
                                geos.add(g);
                            }
                            if (count == 5) {
                                Collections.sort(geos, Geocode.MileageComparator);
                                ParseJson.parseToFile(geos);
                            }
                        }

                        @Override
                        public void fetchFinished() {

                        }

                        @Override
                        public void fetchFail() {

                        }
                    });

                    Thread t = new Thread(pathRunnable);
                    t.start();
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
