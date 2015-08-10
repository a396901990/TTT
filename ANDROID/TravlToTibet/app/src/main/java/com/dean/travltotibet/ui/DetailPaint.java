package com.dean.travltotibet.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.util.Constants;

public class DetailPaint
    extends Paint
{
    private float maxSize;

    private float minSize;

    private int textRectColor;

    private Double stopPercent;

    private Double displayPercent;

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
        case Constants.COUNTY:
            maxSize = 25;
            minSize = 10;
            displayPercent = 1.0d;
            stopPercent = 0.5d;
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_city_paint;
            break;

        case Constants.TOWN:
            maxSize = 20;
            minSize = 10;
            displayPercent = 0.8d;
            stopPercent = 0.4d;
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_town_paint;
            break;

        case Constants.VILLAGE:
            maxSize = 20;
            minSize = 10;
            displayPercent = 0.8d;
            stopPercent = 0.4d;
            textRectColor = TTTApplication.getResourceUtil().chart_text_rect_village_paint;
            break;


            case Constants.HOTEL:
                maxSize = 16;
                minSize = 10;
                displayPercent = 0.2d;
                stopPercent = 0.1d;
                textRectColor = TTTApplication.getResourceUtil().chart_text_rect_hotel_paint;
                break;

            case Constants.SCENIC_SPOT:
                maxSize = 16;
                minSize = 10;
                displayPercent = 0.2d;
                stopPercent = 0.1d;
                textRectColor = TTTApplication.getResourceUtil().chart_text_rect_view_paint;
                break;
        case Constants.MOUNTAIN:
            maxSize = 20;
            minSize = 10;
            displayPercent = 0.2d;
            stopPercent = 0.1d;
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
     * 计算字体大小 当currentViewPoint扩大到displayPercent时显示并放大字体，当扩大到stopPercent时停止放大
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
        // 重新计算当前屏幕大小
        mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

        if (currentViewPoint.width() <= mRect.width() * displayPercent)
        {
            if (currentViewPoint.width() > mRect.width() * stopPercent)
            {
                double p = (mRect.width() - currentViewPoint.width()) / (mRect.width() * (1.0d - stopPercent));
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
     * @param point
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
        if (getTextSize() != 0) {
            canvas.drawCircle(x, y, 5, textRectPaint);
        }
        canvas.drawText(name, x, y - margin, this);
        // expand point border in order to touch easy
        // point.setPointRect(new RectF(left - margin * 3, top - margin * 3, right + margin * 3, bottom + margin * 3));
        point.setPointRect(rect);
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
