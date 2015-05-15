package com.dean.travltotibet;

import java.util.ArrayList;


import com.dean.travltotibet.ChartIndicatorUtil.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndicatorChartView
    extends View
{
    private ArrayList<AbstractSeries> mSeries = new ArrayList<AbstractSeries>();

    private ChartIndicatorUtil mIndicatorUtil;

    private RouteChartView mChartView;

    private Rect mContentRect = new Rect();

    private RectF mCurrentViewport = new RectF();

    private Paint mAxisPaint;

    public IndicatorChartView( Context context )
    {
        super(context);
    }

    public IndicatorChartView( Context context, AttributeSet attrs )
    {
        this(context, attrs, 0);
    }

    public IndicatorChartView( Context context, AttributeSet attrs, int defStyle )
    {
        super(context, attrs, defStyle);
        initPaints();
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh )
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentRect.set(this.getPaddingLeft(), this.getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        initIndicator();
    }

    private void initPaints()
    {
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mAxisPaint.setStrokeWidth(5);
        mAxisPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw( Canvas canvas )
    {
        super.onDraw(canvas);

        int clipRestoreCount = canvas.save();
        canvas.clipRect(mContentRect);

        for (AbstractSeries series : mSeries)
        {
            // series.drawLine(canvas, mContentRect, mCurrentViewport);
            series.drawMountain(canvas, mContentRect, mCurrentViewport);
        }
        mIndicatorUtil.drawIndicator(canvas);

        // Removes clipping rectangle
        canvas.restoreToCount(clipRestoreCount);
        canvas.drawRect(mContentRect, mAxisPaint);
    }

    private OnChartListener mChartListener = new OnChartListener()
        {

            @Override
            public void onChartScroll( RectF mChartViewprot )
            {
                mIndicatorUtil.updateIndicator(mChartViewprot);
            }

            @Override
            public void onChartScale( RectF mChartViewprot )
            {
                mIndicatorUtil.updateIndicator(mChartViewprot);
            }
        };

    @Override
    public boolean onTouchEvent( MotionEvent event )
    {
        return mIndicatorUtil.handleIndicator(event);
    }

    public void clearSeries()
    {
        mSeries = new ArrayList<AbstractSeries>();
        invalidate();
    }

    public void addSeries( AbstractSeries series )
    {
        if (mSeries == null)
        {
            mSeries = new ArrayList<AbstractSeries>();
        }

        mSeries.add(series);
        invalidate();
    }

    public void initIndicator()
    {
        if (mIndicatorUtil == null)
        {
            mIndicatorUtil = new ChartIndicatorUtil(getContext(), this, mSeries.get(0));
        }

        addIndicatorListener(mChartView.getIndicatorListener());
        mChartView.setChartListener(mChartListener);
    }

    public void addIndicatorListener( OnIndicatorListener mIndicatorListener )
    {
        if (mIndicatorUtil != null)
        {
            mIndicatorUtil.setListener(mIndicatorListener);
        }
    }

    public Rect getContentRect()
    {
        return mContentRect;
    }

    public void setContentRect( Rect mContentRect )
    {
        this.mContentRect = mContentRect;
    }

    public RectF getCurrentViewport()
    {
        return mCurrentViewport;
    }

    public void setCurrentViewport( RectF mCurrentViewport )
    {
        this.mCurrentViewport = mCurrentViewport;
    }

    public RouteChartView getChartView()
    {
        return mChartView;
    }

    public void setChartView( RouteChartView mChartView )
    {
        this.mChartView = mChartView;
        setCurrentViewport(mChartView.getCurrentViewport());
    }

    public OnChartListener getChartListener()
    {
        return mChartListener;
    }

    public void setChartListener( OnChartListener mChartListener )
    {
        this.mChartListener = mChartListener;
    }
}
