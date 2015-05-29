package com.daen.google;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {

    public static void main(String[] args) throws Exception {

        final ArrayList<Geocode> geocodes = new ArrayList<Geocode>();
        final Geocode geocode = new Geocode();
        geocode.setName("Ҷ����");
        final Geocode geocode1 = new Geocode();
        geocode1.setName("�¿�����");
        final Geocode geocode2 = new Geocode();
        geocode2.setName("������");
        final Geocode geocode3 = new Geocode();
        geocode3.setName("���������ش�");
        final Geocode geocode4 = new Geocode();
        geocode4.setName("���");

        geocodes.add(geocode);
        geocodes.add(geocode1);
        geocodes.add(geocode2);
        geocodes.add(geocode3);
        geocodes.add(geocode4);

        final ArrayList<Geocode> geos = new ArrayList<Geocode>();
        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

                geos.add(geocode);

                if (geos.size() == geocodes.size()) {
                    System.out.print(geos);
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
