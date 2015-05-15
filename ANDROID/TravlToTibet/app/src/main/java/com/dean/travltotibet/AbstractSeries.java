package com.dean.travltotibet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class AbstractSeries
{
    protected float mScaleX = 1;

    protected float mScaleY = 1;

    private List<AbstractPoint> mPoints;

    private boolean mPointsSorted = false;

    private double mMinX = Double.MAX_VALUE;

    private double mMaxX = Double.MIN_VALUE;

    private double mMinY = Double.MAX_VALUE;

    private double mMaxY = Double.MIN_VALUE;

    private double mRangeX = 0;

    private double mRangeY = 0;
    
    public Context mContext;

    protected abstract void drawPoint( Canvas canvas, AbstractPoint point, Rect contentRect, RectF currentViewPoint );

    protected abstract void drawText( Canvas canvas, Rect contentRect, RectF currentViewPoint );

    protected abstract void drawLine( Canvas canvas, Rect contentRect, RectF currentViewPoint );

    protected abstract void drawMountain( Canvas canvas, Rect contentRect, RectF currentViewPoint );

    public List<AbstractPoint> getPoints()
    {
        Collections.sort(mPoints);
        return mPoints;
    }

    public void setPoints( List<? extends AbstractPoint> points )
    {
        mPoints = new ArrayList<AbstractPoint>();
        mPoints.addAll(points);

        sortPoints();
        resetRange();

        for (AbstractPoint point : mPoints)
        {
            extendRange(point.getX(), point.getY());
        }
    }

    public void addPoint( AbstractPoint point )
    {
        if (mPoints == null)
        {
            mPoints = new ArrayList<AbstractPoint>();
        }

        extendRange(point.getX(), point.getY());
        mPoints.add(point);

        mPointsSorted = false;
    }

    public void sortPoints()
    {
        if (!mPointsSorted)
        {
            Collections.sort(mPoints);
            mPointsSorted = true;
        }
    }

    private void resetRange()
    {
        mMinX = Double.MAX_VALUE;
        mMaxX = Double.MIN_VALUE;
        mMinY = Double.MAX_VALUE;
        mMaxY = Double.MIN_VALUE;

        mRangeX = 0;
        mRangeY = 0;
    }

    private void extendRange( double x, double y )
    {
        if (x < mMinX)
        {
            mMinX = x;
        }

        if (x > mMaxX)
        {
            mMaxX = x;
        }

        if (y < mMinY)
        {
            mMinY = y;
        }

        if (y > mMaxY)
        {
            mMaxY = y;
        }

        mRangeX = mMaxX - mMinX;
        mRangeY = mMaxY - mMinY;
    }

    double getMinX()
    {
        return mMinX;
    }

    double getMaxX()
    {
        return mMaxX;
    }

    double getMinY()
    {
        return mMinY;
    }

    double getMaxY()
    {
        return mMaxY;
    }

    double getRangeX()
    {
        return mRangeX;
    }

    double getRangeY()
    {
        return mRangeY;
    }

    public double getPointY( double X )
    {
        List<AbstractPoint> points = getPoints();

        for (int i = 0; i < points.size(); i++)
        {
            AbstractPoint p = points.get(i);
            if (p.getX() > X && i > 0)
            {
                double x1 = points.get(i - 1).getX();
                double y1 = points.get(i - 1).getY();
                double x2 = p.getX();
                double y2 = p.getY();

                if (y2 == y1)
                {
                    return p.getY();
                }
                else
                {
                    double rate = (y2 - y1) / (x2 - x1);
                    return y1 + (X - x1) * rate;
                }
            }
            else if (p.getX() == X)
            {
                return p.getY();
            }
            else if (p.getX() > X && i == 0)
            {
                return p.getY();
            }
            else if (p.getX() < X && i == points.size() - 1)
            {
                return points.get(points.size() - 1).getY();
            }
        }
        return X;
    }

    protected void onDrawingComplete()
    {
    }
}