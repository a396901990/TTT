package com.dean.travltotibet.model;

import java.io.Serializable;

/**
 * Created by DeanGuo on 3/17/16.
 */
public class GalleryInfo implements Serializable{

    private String url;
    private String name;
    private String objectId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
