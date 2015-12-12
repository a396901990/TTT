package com.dean.travltotibet.ui.chart;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.dean.travltotibet.ui.chart.AbstractPoint;
import com.dean.travltotibet.ui.chart.AbstractSeries;
import com.dean.travltotibet.ui.chart.MountainSeries;
import com.dean.travltotibet.ui.chart.RouteChartView;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.Constants;

public class ChartCrosshairUtil
{
    public static interface OnCrosshairPainted
    {
        void onCrosshairPainted(AbstractPoint point);
    }

    private static final int DELAY_START = 300;

    private static final int CENTER_WIDTH = 5;

    private static final int CENTER_BLANK = 15;

    private final Handler mHandler;

    private final RouteChartView mChart;

    private AbstractSeries mSeries;

    private Paint centerPaint;

    private Paint linePaint;

    private Paint dialogPaint;

    private Paint arrowPaint;

    private TextPaint arrowTextPaint;

    private TextPaint textPaint;

    private float mDownX;

    private boolean mStarted;

    private int mCurrentX;

    private int mCurrentY;

    private final Runnable mStartCrosshair = new Runnable()
        {
            @Override
            public void run()
            {
                mStarted = true;
                invalidate();
            }
        };

    private final Runnable mEndCrosshair = new Runnable()
        {
            @Override
            public void run()
            {
                invalidate();
            }
        };

    private final Runnable mMoveCrosshair = new Runnable()
        {
            @Override
            public void run()
            {
                invalidate();
            };
        };

    private final Runnable mMoveArrow = new Runnable()
        {
            @Override
            public void run()
            {
                invalidate();
            }
        };

    private OnCrosshairPainted mListener;

    public ChartCrosshairUtil( final RouteChartView chart, final AbstractSeries series )
    {
        mChart = chart;
        mSeries = series;

        mHandler = new Handler(Looper.getMainLooper());

        initPaint();
    }

    private void initPaint()
    {
        centerPaint = new Paint();
        centerPaint.setAntiAlias(true);
        centerPaint.setStyle(Style.FILL);
        centerPaint.setColor(TTTApplication.getResourceUtil().chart_cross);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Style.STROKE);
        linePaint.setColor(TTTApplication.getResourceUtil().chart_cross);
        linePaint.setStrokeWidth(4);
        linePaint.setPathEffect(new DashPathEffect(new float[]
            { 5, 5, 5, 5 }, 1));

        dialogPaint = new Paint();
        dialogPaint.setAntiAlias(true);
        dialogPaint.setColor(TTTApplication.getResourceUtil().chart_cross);
        dialogPaint.setAlpha((int) (TTTApplication.getResourceUtil().chart_cross_dialog_alpha));

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);
        textPaint.setColor(TTTApplication.getResourceUtil().chart_cross_text);
        textPaint.setTextAlign(Align.LEFT);

        arrowPaint = new Paint();
        arrowPaint.setAntiAlias(true);
        arrowPaint.setStyle(Style.FILL);
        arrowPaint.setColor(TTTApplication.getResourceUtil().chart_arrow);

        arrowTextPaint = new TextPaint();
        arrowTextPaint.setAntiAlias(true);
        arrowTextPaint.setTextSize(mChart.getLabelTextSize());
        arrowTextPaint.setColor(TTTApplication.getResourceUtil().chart_cross_text);
        arrowTextPaint.setTextAlign(Align.LEFT);
    }

    public AbstractSeries getSeries()
    {
        return mSeries;
    }

    /** 处理触摸事件 **/
    public boolean handleCrosshair( final MotionEvent event )
    {
        boolean handled = mStarted;
        mCurrentX = (int) event.getX();
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
        case MotionEvent.ACTION_DOWN:
            mDownX = mCurrentX;
            if (mStarted)
            {
                mStarted = false;
            }
            mHandler.removeCallbacks(mStartCrosshair);
            mHandler.postDelayed(mStartCrosshair, DELAY_START);
            break;
        case MotionEvent.ACTION_MOVE:
            mHandler.post(mMoveArrow);
            if (mStarted)
            {
                Rect rect = mChart.getContentRect();
                if (mCurrentX >= rect.left && mCurrentX <= rect.right)
                {
                    mHandler.post(mMoveCrosshair);
                }
            }
            else if (Math.abs(mCurrentX - mDownX) > 10)
            {
                mHandler.removeCallbacks(mStartCrosshair);
            }
            break;
        default:
            mHandler.removeCallbacks(mStartCrosshair);
            mHandler.removeCallbacks(mMoveCrosshair);
            if (mStarted)
            {
                mHandler.post(mEndCrosshair);
            }
            break;
        }
        return handled;
    }

    public void setListener( final OnCrosshairPainted listener )
    {
        mListener = listener;
    }

    public void setSeries( final AbstractSeries series )
    {
        this.mSeries = series;
    }

    private boolean cantDraw()
    {
        return !mStarted;
    }

    public void drawArrow( Canvas canvas )
    {
        float arrowLength = mChart.getLabelSeparation() + mChart.getMaxLabelWidth() + mChart.getLabelSeparation() / 2;
        float arrowHeight = mChart.getLabelSeparation() + mChart.getLabelSeparation();

        int arrowX = mChart.getContentRect().right;
        AbstractPoint point = calcPoint(arrowX);
        int arrowY = (int) point.getY(mChart.getContentRect(), mChart.getCurrentViewport());

        Path path = new Path();
        path.moveTo(arrowX, arrowY);
        path.lineTo(arrowX + mChart.getLabelSeparation(), arrowY - arrowHeight);
        path.lineTo(arrowX + arrowLength, arrowY - arrowHeight);
        path.lineTo(arrowX + arrowLength, arrowY + arrowHeight);
        path.lineTo(arrowX + mChart.getLabelSeparation(), arrowY + arrowHeight);
        path.lineTo(arrowX, arrowY);

        canvas.drawPath(path, arrowPaint);
        canvas.drawText((int) point.getY() + "", arrowX + mChart.getLabelSeparation(), arrowY - ((arrowPaint.ascent()) / 2), arrowTextPaint);
    }

    public void drawCrosshair( Canvas canvas )
    {
        if (cantDraw())
        {
            return;
        }
        AbstractPoint point = calcPoint(mCurrentX);
        mCurrentY = (int) point.getY(mChart.getContentRect(), mChart.getCurrentViewport());

        // 中心点
        canvas.drawRect(mCurrentX - CENTER_WIDTH, mCurrentY - CENTER_WIDTH, mCurrentX + CENTER_WIDTH, mCurrentY + CENTER_WIDTH, centerPaint);
        // 圆右方的线
        canvas.drawLine(mCurrentX + CENTER_BLANK, mCurrentY, mChart.getContentRect().right, mCurrentY, linePaint);
        // 圆左方的线
        canvas.drawLine(mCurrentX - CENTER_BLANK, mCurrentY, mChart.getContentRect().left, mCurrentY, linePaint);
        // 圆上方的线
        canvas.drawLine(mCurrentX, mCurrentY - CENTER_BLANK, mCurrentX, mChart.getContentRect().top, linePaint);
        // 圆下方的线
        canvas.drawLine(mCurrentX, mCurrentY + CENTER_BLANK, mCurrentX, mChart.getContentRect().bottom, linePaint);

        StringBuffer mText = new StringBuffer();
        mText.append(Constants.NAME_HEIGHT + (int) point.getY() + "M" + "\n");
        mText.append(Constants.NAME_MILEAGE + (int) point.getX() + "KM");

        Rect charRect = mChart.getContentRect();
        RectF dialogRect = new RectF();
        StaticLayout mStaticLayout = new StaticLayout(mText, textPaint, calcMaxTextLength(mText.toString()), Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);

        float DIALOG_SPACE = textPaint.getFontSpacing() / 2;
        float dialogWidth = mStaticLayout.getWidth() + DIALOG_SPACE;
        float dialogHeight = mStaticLayout.getHeight() + DIALOG_SPACE;
        float textLeft, textTop;
        if (mCurrentX >= (charRect.left + charRect.width() / 2))
        {
            // 第一象限(3)
            if (mCurrentY <= (charRect.top + charRect.height() / 2))
            {
                dialogRect.set(mCurrentX - dialogWidth, mCurrentY, mCurrentX, mCurrentY + dialogHeight);
                textLeft = mCurrentX - dialogWidth + DIALOG_SPACE / 2;
                textTop = mCurrentY + DIALOG_SPACE / 2;
            }
            // 第四象限(2)
            else
            {
                dialogRect.set(mCurrentX - dialogWidth, mCurrentY - dialogHeight, mCurrentX, mCurrentY);
                textLeft = mCurrentX - dialogWidth + DIALOG_SPACE / 2;
                textTop = mCurrentY - dialogHeight + DIALOG_SPACE / 2;
            }
        }
        else
        {
            // 第二象限(4)
            if (mCurrentY <= (charRect.top + charRect.height() / 2))
            {
                dialogRect.set(mCurrentX, mCurrentY, mCurrentX + dialogWidth, mCurrentY + dialogHeight);
                textLeft = mCurrentX + DIALOG_SPACE / 2;
                textTop = mCurrentY + DIALOG_SPACE / 2;
            }
            // 第三象限(1)
            else
            {
                dialogRect.set(mCurrentX, mCurrentY - dialogHeight, mCurrentX + dialogWidth, mCurrentY);
                textLeft = mCurrentX + DIALOG_SPACE / 2;
                textTop = mCurrentY - dialogHeight + DIALOG_SPACE / 2;
            }
        }

        // 画对话框
        canvas.drawRoundRect(dialogRect, 8, 8, dialogPaint);

        // 画文字
        canvas.save();
        canvas.translate(textLeft, textTop);
        mStaticLayout.draw(canvas);
        canvas.restore();

        notifyListener(point);
    }

    private int calcMaxTextLength( String text )
    {
        String[] textArrays = text.split("\n");
        float textLength = 0;
        for (String t : textArrays)
        {
            textLength = Math.max(textPaint.measureText(t), textLength);
        }
        return (int) textLength;
    }

    /** 根据屏幕上X的坐标计算出实际点 */
    private AbstractPoint calcPoint( int X )
    {
        Rect contentRect = mChart.getContentRect();
        RectF currentViewPoint = mChart.getCurrentViewport();

        float scaleX = (float) contentRect.width() / (float) currentViewPoint.width();
        double pointX = ((X - contentRect.left) / scaleX) + currentViewPoint.left;
        if (pointX <= mSeries.getPoints().get(0).getX())
        {
            pointX = (int) mSeries.getPoints().get(0).getX();
        }
        else if (pointX >= mSeries.getPoints().get(mSeries.getPoints().size() - 1).getX())
        {
            pointX = (int) mSeries.getPoints().get(mSeries.getPoints().size() - 1).getX();
        }

        double pointY = mSeries.getPointY(pointX);

        return new MountainSeries.MountainPoint(pointX, pointY);
    }

    private void invalidate()
    {
        mChart.invalidate();
    }

    private void notifyListener( final AbstractPoint point )
    {
        if (mListener != null)
        {
            mListener.onCrosshairPainted(point);
        }
    }
}
