package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 9/26/15.
 */
public enum TravelType {
    BIKE, HIKE, MOTO, CAR;

    public static String getTypeValue(TravelType type) {
        String value = null;
        switch (type) {
            case BIKE:
                value = "BIKE";
                break;
            case HIKE:
                value = "HIKE";
                break;
            case MOTO:
                value = "MOTO";
                break;
            case CAR:
                value = "CAR";
                break;
        }
        return value;
    }
}
