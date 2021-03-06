package com.dean.travltotibet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class DetailPaint
    extends Paint
{
    private float maxSize;

    private float minSize;

    private int color;

    private Double stopPrecent;

    private Double displayPrecent;

    private int category;

    private RectF mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

    public DetailPaint( int category )
    {
        this.category = category;
        this.setAntiAlias(true);
        
        switch (category)
        {
        case Constants.CITY:
            maxSize = 25;
            minSize = 10;
            displayPrecent = 1.0d;
            stopPrecent = 0.5d;
            color = Color.RED;
            break;

        case Constants.TOWN:
            maxSize = 20;
            minSize = 10;
            displayPrecent = 0.8d;
            stopPrecent = 0.4d;
            color = Color.GREEN;
            break;

        case Constants.MOUNTAIN:
            maxSize = 16;
            minSize = 10;
            displayPrecent = 0.6d;
            stopPrecent = 0.3d;
            color = Color.GRAY;
            break;
        default:
            break;
        }

        setColor(color);
        setTextAlign(Align.CENTER);
    }

    /**
     * 计算字体大小
     * 当currentViewPoint扩大到displayPrecent时显示并放大字体，当扩大到stopPrecent时停止放大
     * @param point 点
     * @param contentRect 屏幕
     * @param currentViewPoint 视口
     */
    public void calcSize( AbstractPoint point, Rect contentRect, RectF currentViewPoint )
    {
        if (currentViewPoint.width() <= mRect.width() * displayPrecent)
        {
            if (currentViewPoint.width() > mRect.width() * stopPrecent)
            {
                double p = (mRect.width() - currentViewPoint.width()) / (mRect.width() * (1.0d - stopPrecent));
                float textSize = (float) (minSize + (maxSize - minSize) * p);
                setTextSize(textSize);
            }
            else
            {
                setTextSize(maxSize);
            }
        }
        else
        {
            setTextSize(0);
        }
    }

    /**
     * 在点(x,y) 上画文字/图标
     * @param canvas
     * @param name
     * @param x
     * @param y
     */
    public void drawText( Canvas canvas, String name, float x, float y )
    {
        canvas.drawText(name, x, y - getTextSize(), this);
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory( int category )
    {
        this.category = category;
    }

}
