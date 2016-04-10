package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.module.Geocode;
import com.daen.google.module.LatLng;
import com.daen.google.util.DataGeneratorUtil;
import com.daen.google.util.ParseJson;

import java.io.File;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
 * Created by DeanGuo on 4/10/16.
 */
public class KMLGenerator {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();

        final Kml kml = Kml.unmarshal(new File("/Users/DeanGuo/Desktop/bingchacha.kml"));
        Document document = (Document) kml.getFeature();

        Folder folder = (Folder) document.getFeature().get(0);
        Folder childFolder = (Folder) ((Folder) folder.getFeature().get(0)).getFeature().get(0);
        int folderSize = childFolder.getFeature().size();

        // loop over all countries / Placemarks
        for (int i = folderSize-1; i >= 0; i--) {

            Placemark placemark = (Placemark) childFolder.getFeature().get(i);
            Point point = (Point) placemark.getGeometry();
            Coordinate coordinate = point.getCoordinates().get(0);

            Geocode geocode = new Geocode();
            geocode.setName(placemark.getName());
            geocode.setLongitude(coordinate.getLongitude());
            geocode.setLatitude(coordinate.getLatitude());
            geocode.setElevation(coordinate.getAltitude());
            geocode.setAddress("");
            geocode.setMileage(0);
            geocode.setMilestone(0);
            geocode.setTypes(Constants.PATH);

            // distance
            double distance = 0;
            if (i > 0) {
                Placemark nextPlacemark = (Placemark) childFolder.getFeature().get(i-1);
                Point nextPoint = (Point) nextPlacemark.getGeometry();
                Coordinate nextCoordinate = nextPoint.getCoordinates().get(0);

                LatLng firstLatlng = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
                LatLng secondLatlng = new LatLng(nextCoordinate.getLatitude(), nextCoordinate.getLongitude());
                distance = DataGeneratorUtil.getDistance(firstLatlng, secondLatlng);
            }

            geocode.setDistance(distance);
            geocodes.add(geocode);
        }

        geocodes = DataGeneratorUtil.getNewGeocodes(geocodes, 280);
        ParseJson.parseToFile(geocodes);
    }
}
