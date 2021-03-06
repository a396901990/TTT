package com.dean.travltotibet;

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
import android.text.TextUtils;

public class MountainSeries
    extends AbstractSeries
{
    private Paint mMountainPaint;

    public Paint mLinePaint;

    public PointF mLastPoint;

    private int mMountainColor = Color.BLUE;

    private DetailPaint cityPaint;

    private DetailPaint townPaint;

    private DetailPaint mountainPaint;
    
    public MountainSeries()
    {
        mMountainPaint = new Paint();
        mMountainPaint.setAntiAlias(true);
        mMountainPaint.setAlpha((int) (255 * 0.8));

        cityPaint = new DetailPaint(Constants.CITY);
        townPaint = new DetailPaint(Constants.TOWN);
        mountainPaint = new DetailPaint(Constants.MOUNTAIN);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.BLUE);
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

    public static class MountainPoint
        extends AbstractPoint
    {
        public MountainPoint()
        {
            super();
        }

        public MountainPoint( double x, double y )
        {
            super(x, y, "", 0);
        }

        public MountainPoint( double x, double y, String name )
        {
            super(x, y, name, 0);
        }

        public MountainPoint( double x, double y, String name, int category )
        {
            super(x, y, name, category);
        }
    }

    public void setMountainAlpha( double d )
    {
        mMountainPaint.setAlpha((int) (255 * d));
    }

    public void setMountainColor( int color )
    {
        mMountainColor = color;
    }

    @Override
    protected void drawText( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        sortPoints();
        DetailPaint mPaint = null;

        for (AbstractPoint point : mPoints)
        {
            if (!TextUtils.isEmpty(point.getName()))
            {
                switch (point.getPriority())
                {
                case Constants.CITY:
                    mPaint = cityPaint;
                    break;
                case Constants.TOWN:
                    mPaint = townPaint;
                    break;
                case Constants.MOUNTAIN:
                    mPaint = mountainPaint;
                    break;
                default:
                    break;
                }

                if (mPaint != null)
                {
                    float x = point.getX(contentRect, currentViewPoint);
                    float y = point.getY(contentRect, currentViewPoint);

                    mPaint.calcSize(point, contentRect, currentViewPoint);
                    mPaint.drawText(canvas, point.getName(), x, y);
                }
            }

        }

    }

    @Override
    protected void drawLine( Canvas canvas, Rect contentRect, RectF currentViewPoint )
    {
        List<AbstractPoint> mPoints = getPoints();
        // mPoints.add(0, new MountainPoint(mPoints.get(0).getX(),0));
        // mPoints.add(mPoints.size(),new
        // MountainPoint(mPoints.get(mPoints.size()-1).getX(),0));
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

        // set shader for mountain
        Shader mShader = new LinearGradient(0, contentRect.top, 0, contentRect.bottom, mMountainColor, Color.WHITE, Shader.TileMode.CLAMP);
        mMountainPaint.setShader(mShader);
        canvas.drawPath(path, mMountainPaint);
    }

    public void setLineColor( int color )
    {
        mLinePaint.setColor(color);
    }

    public void setLineWidth( float width )
    {
        mLinePaint.setStrokeWidth(width);
    }
}