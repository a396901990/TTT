package com.dean.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ROUTE_ATTENTION.
 */
public class RouteAttention {

    private Long id;
    /** Not-null value. */
    private String route;
    /** Not-null value. */
    private String type;
    /** Not-null value. */
    private String attention_title;
    /** Not-null value. */
    private String attention_detail;

    public RouteAttention() {
    }

    public RouteAttention(Long id) {
        this.id = id;
    }

    public RouteAttention(Long id, String route, String type, String attention_title, String attention_detail) {
        this.id = id;
        this.route = route;
        this.type = type;
        this.attention_title = attention_title;
        this.attention_detail = attention_detail;
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
    public String getType() {
        return type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setType(String type) {
        this.type = type;
    }

    /** Not-null value. */
    public String getAttention_title() {
        return attention_title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAttention_title(String attention_title) {
        this.attention_title = attention_title;
    }

    /** Not-null value. */
    public String getAttention_detail() {
        return attention_detail;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAttention_detail(String attention_detail) {
        this.attention_detail = attention_detail;
    }

}
