package com.daen.google;

/**
 * Created by 95 on 2015/6/5.
 */
public class Direction {

    public static final int DIRECTION_UNIT = 5000;

    private String distance;
    private String points;
    private String summary;

    public Direction(String distance, String points, String summary) {
        this.distance = distance;
        this.points = points;
        this.summary = summary;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
