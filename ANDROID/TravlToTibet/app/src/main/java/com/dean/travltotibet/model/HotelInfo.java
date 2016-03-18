package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 3/18/16.
 */
public class HotelInfo extends BmobObject{
    private int comment;
    String route;

    String hotel_Name;
    String hotelDetail;
    String hotelAddress;
    String hotelTel;
    String hotelBelong;
    String hotelType;
    String hotelPic;

    public String getHotel_Name() {
        return hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        this.hotel_Name = hotel_Name;
    }

    public String getHotelDetail() {
        return hotelDetail;
    }

    public void setHotelDetail(String hotelDetail) {
        this.hotelDetail = hotelDetail;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelTel() {
        return hotelTel;
    }

    public void setHotelTel(String hotelTel) {
        this.hotelTel = hotelTel;
    }

    public String getHotelBelong() {
        return hotelBelong;
    }

    public void setHotelBelong(String hotelBelong) {
        this.hotelBelong = hotelBelong;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getHotelPic() {
        return hotelPic;
    }

    public void setHotelPic(String hotelPic) {
        this.hotelPic = hotelPic;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }


}
