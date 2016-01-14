package com.dean.travltotibet.ui.chart;

import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.chart.AbstractPoint;
import com.dean.travltotibet.ui.chart.AbstractSeries;

/**
 * Created by DeanGuo on 6/13/15.
 */
public class IndicatorSeries
    extends AbstractSeries
{
    private Paint mMountainPaint;

    public Paint mLinePaint;

    public PointF mLastPoint;

    private int mMountainColor;
    
    public IndicatorSeries()
    {
        mMountainPaint = new Paint();
        mMountainPaint.setAntiAlias(true);
        mMountainPaint.setColor(TTTApplication.getResourceUtil().indicator_mountain);
        mMountainPaint.setAlpha(TTTApplication.getResourceUtil().chart_mountain_alpha);

        mMountainColor = TTTApplication.getResourceUtil().indicator_mountain_shader;

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(TTTApplication.getResourceUtil().indicator_mountain_line);
        mLinePaint.setStrokeWidth(0);
    }

    @Override
    public void drawPoint( Canvas canvas, AbstractPoint point, Rect contentRect, RectF currentViewPoint )
    {
        float x = point.getX(contentRect, currentViewPoint);
        float y = point.getY(contentRect, currentViewPoint);

        if (mLastPoint != null)
        {
            canvas.drawLine(mLastPoint.x, mLastPoint.y, x, y, mLinePaint);
        }
        else
        {
            mLastPoint = new PointF();
        }

        mLastPoint.set(x, y);
    }

    @Override
    protected void onDrawingComplete()
    {
        mLastPoint = null;
    }

    public static class IndicatorPoint
        extends AbstractPoint
    {
        public IndicatorPoint()
        {
            super();
        }

        public IndicatorPoint( double x, double y )
        {
            super(x, y);
        }
    }

    @Override
    public void drawLine( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        for (AbstractPoint point : mPoints)
        {
            drawPoint(canvas, point, contentRect, currentViewPoint);
        }
        onDrawingComplete();
    }

    @Override
    public void drawMountain( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        Collections.sort(mPoints);

        Path path = new Path();
        // first point in x axis
        AbstractPoint fistPoint = mPoints.get(0);
        path.moveTo(fistPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        for (AbstractPoint point : mPoints) {
            float x = point.getX(contentRect, currentViewPoint);
            float y = point.getY(contentRect, currentViewPoint);
            path.lineTo(x, y);
        }
        // last point in x axis
        AbstractPoint lastPoint = mPoints.get(mPoints.size() - 1);
        path.lineTo(lastPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        path.lineTo(fistPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        // set shader for mountain
//        Shader mShader = new LinearGradient(0, contentRect.top, 0, contentRect.bottom, mMountainColor, Color.WHITE, Shader.TileMode.CLAMP);
//        mMountainPaint.setShader(mShader);
        canvas.drawPath(path, mMountainPaint);
    }

    @Override
    public void drawText( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
    }
}