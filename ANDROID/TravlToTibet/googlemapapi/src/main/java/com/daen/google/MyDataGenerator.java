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

        geocodes.add(new Geocode("Ҷ����", 0));
        geocodes.add(new Geocode("�¿�����", 62));
        geocodes.add(new Geocode("������", 72));
        geocodes.add(new Geocode("���������ش�", 100, "Ҷ����"));
        geocodes.add(new Geocode("���", 160));
        geocodes.add(new Geocode("�����̲", 487));

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
