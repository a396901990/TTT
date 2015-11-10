package com.dean.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ROUTE.
 */
public class Route {

    private Long id;
    /** Not-null value. */
    private String route;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String day;
    /** Not-null value. */
    private String start;
    /** Not-null value. */
    private String end;
    /** Not-null value. */
    private String distance;
    /** Not-null value. */
    private String type;
    /** Not-null value. */
    private String rank;
    /** Not-null value. */
    private String describe;
    /** Not-null value. */
    private String detail;
    /** Not-null value. */
    private String pic_url;

    public Route() {
    }

    public Route(Long id) {
        this.id = id;
    }

    public Route(Long id, String route, String name, String day, String start, String end, String distance, String type, String rank, String describe, String detail, String pic_url) {
        this.id = id;
        this.route = route;
        this.name = name;
        this.day = day;
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.type = type;
        this.rank = rank;
        this.describe = describe;
        this.detail = detail;
        this.pic_url = pic_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getRoute() {
        return route;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRoute(String route) {
        this.route = route;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getDay() {
        return day;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDay(String day) {
        this.day = day;
    }

    /** Not-null value. */
    public String getStart() {
        return start;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setStart(String start) {
        this.start = start;
    }

    /** Not-null value. */
    public String getEnd() {
        return end;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setEnd(String end) {
        this.end = end;
    }

    /** Not-null value. */
    public String getDistance() {
        return distance;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /** Not-null value. */
    public String getType() {
        return type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setType(String type) {
        this.type = type;
    }

    /** Not-null value. */
    public String getRank() {
        return rank;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /** Not-null value. */
    public String getDescribe() {
        return describe;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /** Not-null value. */
    public String getDetail() {
        return detail;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /** Not-null value. */
    public String getPic_url() {
        return pic_url;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

}
