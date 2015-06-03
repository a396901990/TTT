package com.daen.google;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.util.Comparator;

/**
 * Entity mapped to table GEOCODE.
 */
public class Geocode {

    private Long id;
    /**
     * Not-null value.
     */
    private String name;
    private double elevation;
    private double mileage;
    private double latitude;
    private double longitude;
    /**
     * Not-null value.
     */
    private String address;
    /**
     * Not-null value.
     */
    private String types;

    private String belong;

    public Geocode() {
    }

    public Geocode(Long id) {
        this.id = id;
    }

    public Geocode(String name, double mileage, String types) {
        this(name, mileage, "", types);
    }

    public Geocode(String name, double mileage, String belong, String types) {
        this.name = name;
        this.mileage = mileage;
        this.types = types;
        this.belong = belong;
    }

    public Geocode(Long id, String name, double elevation, double mileage, double latitude, double longitude, String address, String types) {
        this.id = id;
        this.name = name;
        this.elevation = elevation;
        this.mileage = mileage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.types = types;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Not-null value.
     */
    public String getName() {
        return name;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setName(String name) {
        this.name = name;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Not-null value.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Not-null value.
     */
    public String getTypes() {
        return types;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public static Comparator<Geocode> MileageComparator = new Comparator<Geocode>() {
        @Override
        public int compare(Geocode g1, Geocode g2) {
            if (g2.mileage > g1.mileage) {
                return -1;
            } else {
                return 1;
            }
        }
    };
}
