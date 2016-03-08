package com.dean.travltotibet.util;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class TeamRequestFilter {
    public static final String DEFAULT = "默认搜索";

    private String destinationFilter = DEFAULT;
    private String typeFilter = DEFAULT;

    public String getDestinationFilter() {
        return destinationFilter;
    }

    public void setDestinationFilter(String destinationFilter) {
        this.destinationFilter = destinationFilter;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }
}
