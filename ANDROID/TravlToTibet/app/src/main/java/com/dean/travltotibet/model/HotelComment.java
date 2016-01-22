package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class HotelComment extends Comment {
    String hotel_name;
    String hotel_belong;

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
