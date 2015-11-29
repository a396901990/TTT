package com.dean.travltotibet.ui;

import java.util.ArrayList;

import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.ChartIndicatorUtil;
import com.dean.travltotibet.util.ChartIndicatorUtil.OnChartListener;
import com.dean.travltotibet.util.ChartIndicatorUtil.OnIndicatorListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndicatorChartView
    extends View
{
    private IndicatorChartView mView;

    private ArrayList<AbstractSeries> mSeries = new ArrayList<AbstractSeries>();

    private ChartIndicatorUtil mIndicatorUtil;

    private RouteChartView mChartView;

    private Rect mContentRect = new Rect();

    private RectF mCurrentViewport = new RectF();

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
        this.setBackgroundColor(TTTApplication.getResourceUtil().indicator_background);
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh )
    {
        super.onSizeChanged(w, h, oldw, oldh);
        // add indicator width space
        mContentRect.set(this.getPaddingLeft() + (int) ChartIndicatorUtil.INDICATOR_WIDTH, this.getPaddingTop(), getWidth() - getPaddingRight() - (int) ChartIndicatorUtil.INDICATOR_WIDTH, getHeight() - getPaddingBottom());
        initIndicator();
    }

    @Override
    protected void onDraw( Canvas canvas )
    {
        super.onDraw(canvas);

        for (AbstractSeries series : mSeries)
        {
            series.drawLine(canvas, mContentRect, mCurrentViewport);
            // series.drawMountain(canvas, mContentRect, mCurrentViewport);
        }
        mIndicatorUtil.drawIndicator(canvas);

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
        mSeries = new ArrayList<AbstractSeries>();

        mSeries.add(series);
        invalidate();
    }

    public void initIndicator()
    {
        mView = this;
        mIndicatorUtil = new ChartIndicatorUtil(mView);

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

}
