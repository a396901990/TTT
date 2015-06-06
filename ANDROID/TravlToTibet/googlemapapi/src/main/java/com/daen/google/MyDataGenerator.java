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

        geocodes.add(new Geocode("Ҷ����", 0, Constants.COUNTY));
        geocodes.add(new Geocode("�¿�����", 62, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 72, Constants.VILLAGE));
        geocodes.add(new Geocode("���������ش�", 100, "Ҷ����", Constants.VILLAGE));
        geocodes.add(new Geocode("���", 160, Constants.TOWN));
        geocodes.add(new Geocode("��ͼ��", 355, Constants.VILLAGE));
        geocodes.add(new Geocode("�����̲", 487, Constants.VILLAGE));
        //geocodes.add(new Geocode("������������", 732, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 829, "������", Constants.TOWN));
        geocodes.add(new Geocode("������", 932, Constants.COUNTY));
        geocodes.add(new Geocode("������", 965, Constants.VILLAGE));
        geocodes.add(new Geocode("ʨȪ����", 1058, Constants.COUNTY));
        geocodes.add(new Geocode("���Ѵ�", 1202, Constants.VILLAGE));
        geocodes.add(new Geocode("�͸�", 1321, "������", Constants.TOWN));
        geocodes.add(new Geocode("������", 1364, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 1585, Constants.TOWN));
        geocodes.add(new Geocode("�ٰ���", 1652, Constants.COUNTY));
        geocodes.add(new Geocode("������", 1760, Constants.VILLAGE));
        geocodes.add(new Geocode("�Ｊ����", 1819, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 1846, Constants.COUNTY));
        geocodes.add(new Geocode("������", 1940, Constants.VILLAGE));
        geocodes.add(new Geocode("ɣɣ��", 2025, Constants.TOWN));
        geocodes.add(new Geocode("������", 2087, Constants.TOWN));
        geocodes.add(new Geocode("������", 2139, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 2146, Constants.COUNTY));
        geocodes.add(new Geocode("������", 2189, Constants.VILLAGE));
        geocodes.add(new Geocode("����", 2213, Constants.VILLAGE));
        geocodes.add(new Geocode("������", 2236, Constants.TOWN));
        geocodes.add(new Geocode("�տ���", 2291, Constants.CITY));
        geocodes.add(new Geocode("������", 2341, Constants.COUNTY));
        geocodes.add(new Geocode("������", 2382, Constants.COUNTY));
        geocodes.add(new Geocode("���ȴ�", 2407,"������", Constants.VILLAGE));
        geocodes.add(new Geocode("������", 2437, Constants.VILLAGE));
        geocodes.add(new Geocode("�˿�����", 2485, Constants.COUNTY));
        geocodes.add(new Geocode("�����", 2577, Constants.VILLAGE));
        geocodes.add(new Geocode("����", 2651, Constants.CITY));

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
