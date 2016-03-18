package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 3/18/16.
 */
public class ScenicInfo extends BmobObject{
    private int comment;
    String route;
    String scenicName;
    String scenicOverview;
    String scenicDetail;
    String scenicAddress;
    String scenicFTraffic;
    String scenicRTraffic;
    String scenicOpentime;
    String scenicFBelong;
    String scenicRBelong;
    String scenic_Pic;

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

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getScenicOverview() {
        return scenicOverview;
    }

    public void setScenicOverview(String scenicOverview) {
        this.scenicOverview = scenicOverview;
    }

    public String getScenicDetail() {
        return scenicDetail;
    }

    public void setScenicDetail(String scenicDetail) {
        this.scenicDetail = scenicDetail;
    }

    public String getScenicAddress() {
        return scenicAddress;
    }

    public void setScenicAddress(String scenicAddress) {
        this.scenicAddress = scenicAddress;
    }

    public String getScenicFTraffic() {
        return scenicFTraffic;
    }

    public void setScenicFTraffic(String scenicFTraffic) {
        this.scenicFTraffic = scenicFTraffic;
    }

    public String getScenicRTraffic() {
        return scenicRTraffic;
    }

    public void setScenicRTraffic(String scenicRTraffic) {
        this.scenicRTraffic = scenicRTraffic;
    }

    public String getScenicOpentime() {
        return scenicOpentime;
    }

    public void setScenicOpentime(String scenicOpentime) {
        this.scenicOpentime = scenicOpentime;
    }

    public String getScenicFBelong() {
        return scenicFBelong;
    }

    public void setScenicFBelong(String scenicFBelong) {
        this.scenicFBelong = scenicFBelong;
    }

    public String getScenicRBelong() {
        return scenicRBelong;
    }

    public void setScenicRBelong(String scenicRBelong) {
        this.scenicRBelong = scenicRBelong;
    }

    public String getScenic_Pic() {
        return scenic_Pic;
    }

    public void setScenic_Pic(String scenic_Pic) {
        this.scenic_Pic = scenic_Pic;
    }

}
