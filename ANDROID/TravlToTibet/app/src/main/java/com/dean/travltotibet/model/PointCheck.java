package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 11/14/15.
 */
public class PointCheck {

    public interface PointCheckChangedListener {
        public void setToSelectedList(PointCheck pointCheck);
        public void setToUnselectedList(PointCheck pointCheck);
    }

    private String name;
    private boolean isChecked;

    public PointCheck(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
