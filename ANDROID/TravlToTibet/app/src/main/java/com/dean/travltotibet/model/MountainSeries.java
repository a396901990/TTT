package com.dean.travltotibet.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Log;

import com.dean.travltotibet.ui.PointDetailPaint;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.ui.DetailPaint;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.PointManager;

/**
 * Created by DeanGuo on 6/13/15.
 */
public class MountainSeries
        extends AbstractSeries {
    private Paint mMountainPaint;

    public Paint mLinePaint;

    public PointF mLastPoint;

    private int mMountainColor;

    public MountainSeries() {
        mMountainPaint = new Paint();
        mMountainPaint.setAntiAlias(true);
        mMountainPaint.setColor(TTTApplication.getResourceUtil().chart_mountain);
        mMountainPaint.setAlpha(TTTApplication.getResourceUtil().chart_mountain_alpha);

        mMountainColor = TTTApplication.getResourceUtil().chart_mountain_shader;

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Style.FILL);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setColor(TTTApplication.getResourceUtil().chart_line);
    }

    public void initPaint() {
        PointManager.initPointDetailPaint(calcPoint());

    }

    public int calcPoint() {
        List<AbstractPoint> points = getPoints();
        int count = 0;
        for (AbstractPoint p : points) {
            if (!PointManager.PATH.equals(p.getCategory())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void drawPoint(Canvas canvas, AbstractPoint point, Rect contentRect, RectF currentViewPoint) {
        float x = point.getX(contentRect, currentViewPoint);
        float y = point.getY(contentRect, currentViewPoint);

        if (mLastPoint != null) {
            canvas.drawLine(mLastPoint.x, mLastPoint.y, x, y, mLinePaint);
        } else {
            mLastPoint = new PointF();
        }

        mLastPoint.set(x, y);
    }

    @Override
    protected void onDrawingComplete() {
        mLastPoint = null;
    }

    public static class MountainPoint
            extends AbstractPoint {
        public MountainPoint() {
            super();
        }

        public MountainPoint(double x, double y) {
            super(x, y, "", PointManager.PATH);
        }

        public MountainPoint(double x, double y, String name) {
            super(x, y, name, PointManager.PATH);
        }

        public MountainPoint(double x, double y, String name, String category) {
            super(x, y, name, category);
        }
    }

    public void setMountainAlpha(double d) {
        mMountainPaint.setAlpha((int) (255 * d));
    }

    public void setMountainColor(int color) {
        mMountainColor = color;
    }

    @Override
    public void drawText(Canvas canvas, Rect contentRect, RectF currentViewPoint) {
        List<AbstractPoint> mPoints = getPoints();
        sortPoints();
        PointDetailPaint mPaint;

        for (AbstractPoint point : mPoints) {
            if (!TextUtils.isEmpty(point.getName())) {
                // 获取画笔
                mPaint = PointManager.getPaint(point.getCategory());

                if (mPaint != null) {
                    float x = point.getX(contentRect, currentViewPoint);
                    float y = point.getY(contentRect, currentViewPoint);

                    mPaint.calcSize(currentViewPoint);
                    mPaint.drawText(canvas, point, x, y);
                }
            }

        }

    }

    @Override
    public void drawLine(Canvas canvas, Rect contentRect, RectF currentViewPoint) {
        List<AbstractPoint> mPoints = getPoints();
        // mPoints.add(0, new MountainPoint(mPoints.get(0).getX(),0));
        // mPoints.add(mPoints.size(),new
        // MountainPoint(mPoints.get(mPoints.size()-1).getX(),0));
        for (AbstractPoint point : mPoints) {
            drawPoint(canvas, point, contentRect, currentViewPoint);
        }
        onDrawingComplete();
    }

    @Override
    public void drawMountain(Canvas canvas, Rect contentRect, RectF currentViewPoint) {
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
        Shader mShader = new LinearGradient(0, contentRect.top, 0, contentRect.bottom, mMountainColor, Color.WHITE, Shader.TileMode.CLAMP);
        mMountainPaint.setShader(mShader);
        canvas.drawPath(path, mMountainPaint);
    }

    public void setLineColor(int color) {
        mLinePaint.setColor(color);
    }

    public void setLineWidth(float width) {
        mLinePaint.setStrokeWidth(width);
    }
}