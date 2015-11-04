package com.dean.travltotibet.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.util.PointManager;

/**
 * Created by DeanGuo on 11/3/15.
 */
public class PointDetailPaint extends Paint {

    // 点的类型
    private String category;

    // 点的外框
    private RectF mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

    // 文字画笔
    private Paint textRectPaint = new Paint();

    // 一共有多少点
    private int count;

    private static int POINT_MIN = 10;

    private float maxSize;

    private float minSize;

    private Double stopPercent;

    private Double displayPercent;

    public PointDetailPaint(String category) {
        this.category = category;

        // 设置背景画笔
        this.setAntiAlias(true);
        this.setColor(TTTApplication.getResourceUtil().chart_text_paint);
        this.setTextAlign(Align.CENTER);

        // 设置中央字体画笔
        textRectPaint.setAntiAlias(true);
        textRectPaint.setColor(TTTApplication.getResourceUtil().getResources().getColor(PointManager.getColor(category)));
        textRectPaint.setStyle(Style.FILL);
        textRectPaint.setAlpha(TTTApplication.getResourceUtil().chart_text_rect_aplha);
    }

    /**
     * 计算字体大小 当currentViewPoint扩大到displayPercent时显示并放大字体，当扩大到stopPercent时停止放大
     *
     * @param point            点
     * @param contentRect      屏幕
     * @param currentViewPoint 视口
     */
    public void calcSize(AbstractPoint point, Rect contentRect, RectF currentViewPoint) {
        // 重新计算当前屏幕大小
        mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

        if (currentViewPoint.width() <= mRect.width() * displayPercent) {
            if (currentViewPoint.width() > mRect.width() * stopPercent) {
                double p = (mRect.width() - currentViewPoint.width()) / (mRect.width() * (1.0d - stopPercent));
                float textSize = (float) (minSize + (maxSize - minSize) * p);
                setTextSize(textSize);
            } else {
                setTextSize(maxSize);
            }
        } else {
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
    public void drawText(Canvas canvas, AbstractPoint point, float x, float y) {
        String name = point.getName();
        float margin = getTextSize();
        float padding = getTextSize() / 4;

        float left = x - this.measureText(name) / 2 - padding;
        float right = left + this.measureText(name) + padding + padding;
        float top;
        float bottom;

        // 文字Y坐标
        float textY;

        // 如果点数大于10，倒着显示
        if (count > POINT_MIN && (PointManager.VILLAGE.equals(getCategory()) || PointManager.TOWN.equals(getCategory()))) {

            top = y + padding + padding;
            bottom = top + margin + this.descent();
            textY = y + margin + padding + padding;

        } else {

            top = y - margin + this.ascent() - padding;
            bottom = top + padding + padding + margin + this.descent();
            textY = y - margin;
        }

        // calculation text rectangle size and position
        RectF rect = new RectF(left, top, right, bottom);

        canvas.drawRoundRect(rect, 8, 8, textRectPaint);
        if (getTextSize() != 0) {
            canvas.drawCircle(x, y, 5, textRectPaint);
        }
        canvas.drawText(name, x, textY, this);
        // expand point border in order to touch easy
        // point.setPointRect(new RectF(left - margin * 3, top - margin * 3, right + margin * 3, bottom + margin * 3));
        point.setPointRect(rect);
    }

    public String getCategory() {
        return category;
    }

    public void setDisplayPercent(Double stopPercent, Double displayPercent) {
        this.stopPercent = stopPercent;
        this.displayPercent = displayPercent;
    }

    public void setSize(float minSize, float maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
