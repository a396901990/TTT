package com.dean.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PLAN.
 */
public class Plan {

    private Long id;
    /** Not-null value. */
    private String route_plan_id;
    /** Not-null value. */
    private String day;
    /** Not-null value. */
    private String hours;
    /** Not-null value. */
    private String start;
    /** Not-null value. */
    private String end;
    /** Not-null value. */
    private String distance;
    /** Not-null value. */
    private String describe;
    /** Not-null value. */
    private String rank_hard;
    /** Not-null value. */
    private String rank_view;
    /** Not-null value. */
    private String rank_road;

    public Plan() {
    }

    public Plan(Long id) {
        this.id = id;
    }

    public Plan(Long id, String route_plan_id, String day, String hours, String start, String end, String distance, String describe, String rank_hard, String rank_view, String rank_road) {
        this.id = id;
        this.route_plan_id = route_plan_id;
        this.day = day;
        this.hours = hours;
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.describe = describe;
        this.rank_hard = rank_hard;
        this.rank_view = rank_view;
        this.rank_road = rank_road;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getRoute_plan_id() {
        return route_plan_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRoute_plan_id(String route_plan_id) {
        this.route_plan_id = route_plan_id;
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
    public String getHours() {
        return hours;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setHours(String hours) {
        this.hours = hours;
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
    public String getDescribe() {
        return describe;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /** Not-null value. */
    public String getRank_hard() {
        return rank_hard;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRank_hard(String rank_hard) {
        this.rank_hard = rank_hard;
    }

    /** Not-null value. */
    public String getRank_view() {
        return rank_view;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRank_view(String rank_view) {
        this.rank_view = rank_view;
    }

    /** Not-null value. */
    public String getRank_road() {
        return rank_road;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRank_road(String rank_road) {
        this.rank_road = rank_road;
    }

}
