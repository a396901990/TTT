package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DeanGuo on 3/13/16.
 */
public class PrepareInfo extends BmobObject {

    private String route;
    private String travelType;
    private BmobFile budget;
    private BmobFile medicine;
    private BmobFile equip;
    private BmobFile outdoor;
    private BmobFile credential;
    private BmobFile personal;
    private BmobFile attention;
    private BmobFile other;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public BmobFile getBudget() {
        return budget;
    }

    public void setBudget(BmobFile budget) {
        this.budget = budget;
    }

    public BmobFile getMedicine() {
        return medicine;
    }

    public void setMedicine(BmobFile medicine) {
        this.medicine = medicine;
    }

    public BmobFile getEquip() {
        return equip;
    }

    public void setEquip(BmobFile equip) {
        this.equip = equip;
    }

    public BmobFile getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(BmobFile outdoor) {
        this.outdoor = outdoor;
    }

    public BmobFile getCredential() {
        return credential;
    }

    public void setCredential(BmobFile credential) {
        this.credential = credential;
    }

    public BmobFile getPersonal() {
        return personal;
    }

    public void setPersonal(BmobFile personal) {
        this.personal = personal;
    }

    public BmobFile getAttention() {
        return attention;
    }

    public void setAttention(BmobFile attention) {
        this.attention = attention;
    }

    public BmobFile getOther() {
        return other;
    }

    public void setOther(BmobFile other) {
        this.other = other;
    }
}
