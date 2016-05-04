package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.module.Geocode;
import com.daen.google.module.LatLng;
import com.daen.google.util.KMLGeneratorUtil;
import com.daen.google.util.ParseJson;

import java.io.File;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
 * Created by DeanGuo on 4/10/16.
 */
public class KMLGeneratorBCC {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {
        LatLng firstLatlnga = new LatLng(28.557, 98.22337);
        LatLng secondLatlnga = new LatLng(28.559945, 98.212197);
        double distancea = KMLGeneratorUtil.getDistance(firstLatlnga, secondLatlnga);

        geocodes = new ArrayList<>();

        final Kml kml = Kml.unmarshal(new File("/Users/DeanGuo/Desktop/bingchacha.kml"));
        Document document = (Document) kml.getFeature();

        Folder folder = (Folder) document.getFeature().get(0);
        Folder childFolder = (Folder) ((Folder) folder.getFeature().get(0)).getFeature().get(0);
        int folderSize = childFolder.getFeature().size();

        // loop over all countries / Placemarks
        for (int i = 0; i <folderSize ; i++) {

            Placemark placemark = (Placemark) childFolder.getFeature().get(i);
            Point point = (Point) placemark.getGeometry();
            Coordinate coordinate = point.getCoordinates().get(0);

            Geocode geocode = new Geocode();
            geocode.setName(placemark.getName());
            geocode.setLongitude(coordinate.getLongitude());
            geocode.setLatitude(coordinate.getLatitude());
            geocode.setElevation(coordinate.getAltitude());
            geocode.setAddress("");
            geocode.setMileage(i);
            geocode.setMilestone(0);
            geocode.setTypes(Constants.PATH);

            geocodes.add(geocode);
        }
        geocodes = KMLGeneratorUtil.getDistanceForGeo(geocodes);
        ArrayList<Geocode> newGeo = KMLGeneratorUtil.getNewGeocodes(geocodes, 280);
        ArrayList<String> addPoint = new ArrayList<>();
        addPoint.add("-19582");
        addPoint.add("-19450");
        addPoint.add("-18553");
        addPoint.add("-17999");
        addPoint.add("-17998");
        addPoint.add("-17714");
        addPoint.add("-17340");
        addPoint.add("-16740");
        addPoint.add("-16071");
        addPoint.add("-15730");
        addPoint.add("-15640");
        addPoint.add("-15220");
        addPoint.add("-14525");
        addPoint.add("-13580");
        addPoint.add("-12820");
        addPoint.add("-12665");
        addPoint.add("-12510");
        addPoint.add("-11450");
        addPoint.add("-9800");
        addPoint.add("-9380");
        addPoint.add("-7457");
        addPoint.add("-7008");
        addPoint.add("-6100");
        addPoint.add("-5175");
        addPoint.add("-4570");
        addPoint.add("-2560");
        addPoint.add("-1630");
        addPoint.add("-1195");
        addPoint.add("-740");
        addPoint.add("-580");
        addPoint.add("-170");
        addPoint.add("-0");
        newGeo = KMLGeneratorUtil.addPoint(newGeo, geocodes, addPoint);
        newGeo = KMLGeneratorUtil.reOrder(newGeo, true);

        ArrayList<Geocode> finalGeo = KMLGeneratorUtil.getGeoDistance(newGeo, geocodes);
        finalGeo = KMLGeneratorUtil.getReversGeo(finalGeo);

        ParseJson.parseToFile(finalGeo);
    }
}
