package com.dean.travltotibet;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class IndicatorSeries
    extends AbstractSeries
{
    private Paint mMountainPaint;

    public Paint mLinePaint;

    public PointF mLastPoint;
    
    private Context mContext;
    
    public IndicatorSeries(Context context)
    {
        mContext = context;
        
        mMountainPaint = new Paint();
        mMountainPaint.setAntiAlias(true);
        mMountainPaint.setColor(mContext.getResources().getColor(R.color.indicator_mountain));

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(2);
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

    public void setMountainAlpha( double d )
    {
        mMountainPaint.setAlpha((int) (255 * d));
    }

    public void setMountainColor( int color )
    {
        mMountainPaint.setColor(color);
    }

    @Override
    protected void drawLine( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        for (AbstractPoint point : mPoints)
        {
            drawPoint(canvas, point, contentRect, currentViewPoint);
        }
        onDrawingComplete();
    }

    @Override
    protected void drawMountain( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        Collections.sort(mPoints);

        Path path = new Path();
        // first point in x axis
        AbstractPoint fistPoint = mPoints.get(0);
        path.moveTo(fistPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        for (AbstractPoint point : mPoints)
        {
            float x = point.getX(contentRect, currentViewPoint);
            float y = point.getY(contentRect, currentViewPoint);
            path.lineTo(x, y);
        }
        // last point in x axis
        AbstractPoint lastPoint = mPoints.get(mPoints.size() - 1);
        path.lineTo(lastPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        path.lineTo(fistPoint.getX(contentRect, currentViewPoint), contentRect.bottom);

        canvas.drawPath(path, mMountainPaint);
    }

    @Override
    protected void drawText( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
    }
}