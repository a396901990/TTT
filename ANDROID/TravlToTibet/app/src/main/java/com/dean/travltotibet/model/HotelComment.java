package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class HotelComment extends Comment {
    private String hotel_name;
    private String hotel_belong;
    private String route;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_belong() {
        return hotel_belong;
    }

    public void setHotel_belong(String hotel_belong) {
        this.hotel_belong = hotel_belong;
    }
}
