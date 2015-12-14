package com.example.deanguo.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table RECENT_ROUTE.
 */
public class RecentRoute {

    private Long id;
    /** Not-null value. */
    private String route;
    private String route_name;
    private String type;
    private String FR;
    private String route_plan_id;

    public RecentRoute() {
    }

    public RecentRoute(Long id) {
        this.id = id;
    }

    public RecentRoute(Long id, String route, String route_name, String type, String FR, String route_plan_id) {
        this.id = id;
        this.route = route;
        this.route_name = route_name;
        this.type = type;
        this.FR = FR;
        this.route_plan_id = route_plan_id;
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

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFR() {
        return FR;
    }

    public void setFR(String FR) {
        this.FR = FR;
    }

    public String getRoute_plan_id() {
        return route_plan_id;
    }

    public void setRoute_plan_id(String route_plan_id) {
        this.route_plan_id = route_plan_id;
    }

}
