package com.dean.travltotibet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class DetailPaint
    extends Paint
{
    private float maxSize;

    private float minSize;

    private int textRectColor;

    private Double stopPrecent;

    private Double displayPrecent;

    private int category;

    private RectF mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

    private Paint textRectPaint = new Paint();

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
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_city_paint;
            break;

        case Constants.TOWN:
            maxSize = 20;
            minSize = 10;
            displayPrecent = 0.8d;
            stopPrecent = 0.4d;
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_town_paint;
            break;

        case Constants.MOUNTAIN:
            maxSize = 16;
            minSize = 10;
            displayPrecent = 0.6d;
            stopPrecent = 0.3d;
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_mountain_paint;
            break;
        default:
            break;
        }

        setColor(TTTApplication.getResourceUtil().chart_text_paint);
        setTextAlign(Align.CENTER);

        textRectPaint.setAntiAlias(true);
        textRectPaint.setColor(textRectColor);
        textRectPaint.setStyle(Style.FILL);
        textRectPaint.setAlpha(TTTApplication.getResourceUtil().chart_text_rect_aplha);
    }

    /**
     * 计算字体大小 当currentViewPoint扩大到displayPrecent时显示并放大字体，当扩大到stopPrecent时停止放大
     * 
     * @param point
     *            点
     * @param contentRect
     *            屏幕
     * @param currentViewPoint
     *            视口
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
     * 
     * @param canvas
     * @param name
     * @param x
     * @param y
     */
    public void drawText( Canvas canvas, AbstractPoint point, float x, float y )
    {
        String name = point.getName();
        float margin = getTextSize();

        // calculation text rectangle size and position
        float padding = getTextSize() / 4;
        float left = x - this.measureText(name) / 2 - padding;
        float right = left + this.measureText(name) + padding + padding;
        float top = y - margin + this.ascent() - padding;
        float bottom = top + padding + padding + margin + this.descent();
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rect, 8, 8, textRectPaint);
        canvas.drawText(name, x, y - margin, this);

        // expand point border in order to touch easy
        point.setPointRect(new RectF(left - margin * 3, top - margin * 3, right + margin * 3, bottom + margin * 3));
    }

    public void calcTextRect( AbstractPoint point )
    {

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
