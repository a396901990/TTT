package com.dean.travltotibet.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
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

    // 当前点下标位置（优先级）
    private int currentPointIndex;

    // 显示点类型得总数量
    private int currentPointsSize;

    // 字体最大值
    private final static int TEXT_MAX_SIZE = 22;

    // 字体最小值
    private final static int TEXT_MIN_SIZE = 11;

    // 起点终点字体
    private final static int START_END_SIZE = 25;

    // 当所有点得扩大距离到达比例时不再显示 (0-1, 越大显示间距越大)
    private final static Double TEXT_DISPLAY_PERCENT = 0.6;

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
     * @param currentViewPoint 视口
     */
    public void calcSize(RectF currentViewPoint) {
        // start end point
        if (category.equals(PointManager.START_END)) {
            setTextSize(START_END_SIZE);
        }

        // others point
        if (count > POINT_MIN) {
            // 重新计算当前屏幕大小
            mRect = new RectF(RouteChartView.AXIS_X_MIN, RouteChartView.AXIS_Y_MIN, RouteChartView.AXIS_X_MAX, RouteChartView.AXIS_Y_MAX);

            maxSize = TEXT_MAX_SIZE;
            minSize = TEXT_MIN_SIZE;
            displayPercent = 1 - currentPointIndex * (TEXT_DISPLAY_PERCENT / currentPointsSize);
            stopPercent = 1 - (currentPointIndex + 1) * (TEXT_DISPLAY_PERCENT / currentPointsSize);

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
        else {
            setTextSize(TEXT_MAX_SIZE);
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

    public void setCurrentPointIndex(int currentPointIndex) {
        this.currentPointIndex = currentPointIndex;
    }

    public void setCurrentPointsSize(int currentPointsSize) {
        this.currentPointsSize = currentPointsSize;
    }
}
