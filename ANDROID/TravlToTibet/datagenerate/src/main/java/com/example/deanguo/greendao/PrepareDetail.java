package com.example.deanguo.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PREPARE_DETAIL.
 */
public class PrepareDetail {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String travel_type;
    private String type;
    private String title;
    private String summary;
    private String detail;

    public PrepareDetail() {
    }

    public PrepareDetail(Long id) {
        this.id = id;
    }

    public PrepareDetail(Long id, String name, String travel_type, String type, String title, String summary, String detail) {
        this.id = id;
        this.name = name;
        this.travel_type = travel_type;
        this.type = type;
        this.title = title;
        this.summary = summary;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getTravel_type() {
        return travel_type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTravel_type(String travel_type) {
        this.travel_type = travel_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}