package com.dean.travltotibet.model;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by DeanGuo on 6/13/15.
 */
public abstract class AbstractPoint
        implements Comparable<AbstractPoint> {
    private double mX;

    private double mY;

    private String mName;

    private String mCategory;

    private RectF pointRect;

    public AbstractPoint() {
    }

    public AbstractPoint(double x, double y) {
        mX = x;
        mY = y;
    }

    public AbstractPoint(double x, double y, String name) {
        mX = x;
        mY = y;
        mName = name;
    }

    public AbstractPoint(double x, double y, String name, String category) {
        mX = x;
        mY = y;
        mName = name;
        mCategory = category;
    }

    public float getX(Rect contentRect, RectF currentViewPoint) {
        float scaleX = (float) contentRect.width() / (float) currentViewPoint.width();
        float x = (float) ((getX() - currentViewPoint.left) * scaleX + contentRect.left);
        return x;
    }

    public float getY(Rect contentRect, RectF currentViewPoint) {
        float scaleY = (float) contentRect.height() / (float) currentViewPoint.height();
        float y = (float) (contentRect.bottom - (getY() - currentViewPoint.top) * scaleY);
        return y;
    }

    public double getX() {
        return mX;
    }

    public double getY() {
        return mY;
    }

    public void set(double x, double y) {
        mX = x;
        mY = y;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public int compareTo(AbstractPoint another) {
        return Double.compare(mX, another.mX);
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public RectF getPointRect() {
        return pointRect;
    }

    public void setPointRect(RectF textRect) {
        this.pointRect = textRect;
    }
}
