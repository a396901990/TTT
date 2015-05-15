package com.dean.travltotibet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

public class ChartIndicatorUtil
{
    private static final float INDICATOR_WIDTH = 60;

    public static interface OnIndicatorListener
    {
        void onIndicatorChanged(RectF mViewprot);
    }

    public static interface OnChartListener
    {
        void onChartScroll(RectF mChartViewprot);

        void onChartScale(RectF mChartViewprot);
    }

    private Context mContext;

    private Rect mRect;

    private RectF mLeftIndicator = new RectF();

    private RectF mMovingLeftIndicator = new RectF();

    private RectF mMovingRightIndicator = new RectF();

    private RectF mRightIndicator = new RectF();

    private RectF mLeftShadow = new RectF();

    private RectF mRightShadow = new RectF();

    private final IndicatorChartView mIndicator;

    private AbstractSeries mSeries;

    private Paint indicatorPaint;

    private Paint shadowPaint;

    private int mCurrentX;

    private int mCurrentY;

    private float indicatorPaddingLeft = 0;

    private float indicatorPaddingRight = 0;

    boolean isLeft, isRight, isCenter, isMove;

    private OnIndicatorListener mListener;

    private float limit;

    private final Handler mHandler = new Handler();

    private final Runnable mMoveLeftRunnbale = new Runnable()
        {
            @Override
            public void run()
            {
                isMove = true;

                float left = 0;

                if ((mCurrentX - INDICATOR_WIDTH / 2) < mRect.left)
                {
                    left = mRect.left;
                }
                else if (mCurrentX + INDICATOR_WIDTH / 2 >= mRightIndicator.left)
                {
                    left = mRightIndicator.left - INDICATOR_WIDTH;
                }
                else
                {
                    left = mCurrentX - INDICATOR_WIDTH / 2;
                }

                if (left + INDICATOR_WIDTH + limit > mRightIndicator.left)
                {
                    left = mRightIndicator.left - limit - INDICATOR_WIDTH;
                }
                mMovingLeftIndicator.set(left, mRect.top, left + INDICATOR_WIDTH, mRect.bottom);
                invalidate();
            }
        };

    private final Runnable mMoveRightRunnbale = new Runnable()
        {
            @Override
            public void run()
            {
                isMove = true;

                float right = 0;
                if ((mCurrentX + INDICATOR_WIDTH / 2) > mRect.right)
                {
                    right = mRect.right;
                }
                else if (mCurrentX - INDICATOR_WIDTH / 2 <= mLeftIndicator.right)
                {
                    right = mLeftIndicator.right + INDICATOR_WIDTH;
                }
                else
                {
                    right = mCurrentX + INDICATOR_WIDTH / 2;
                }

                if (right - INDICATOR_WIDTH - limit < mLeftIndicator.right)
                {
                    right = mLeftIndicator.right + limit + INDICATOR_WIDTH;
                }

                mMovingRightIndicator.set(right - INDICATOR_WIDTH, mRect.top, right, mRect.bottom);
                invalidate();
            }
        };

    private final Runnable mMoveCenterRunnbale = new Runnable()
        {
            @Override
            public void run()
            {
                float left = 0;
                float right = 0;
                if (mCurrentX < indicatorPaddingLeft)
                {
                    left = mRect.left;
                    right = left + indicatorPaddingLeft + indicatorPaddingRight;
                }
                else if (mCurrentX > mRect.right - indicatorPaddingRight)
                {
                    right = mRect.right;
                    left = right - indicatorPaddingLeft - indicatorPaddingRight;
                }
                else
                {
                    left = mCurrentX - indicatorPaddingLeft;
                    right = mCurrentX + indicatorPaddingRight;
                }

                mLeftIndicator.set(left, mRect.top, left + INDICATOR_WIDTH, mRect.bottom);
                mRightIndicator.set(right - INDICATOR_WIDTH, mRect.top, right, mRect.bottom);
                mLeftShadow.set(mRect.left, mRect.top, mLeftIndicator.left, mRect.bottom);
                mRightShadow.set(mRightIndicator.right, mRect.top, mRect.right, mRect.bottom);

                mListener.onIndicatorChanged(calcCenterRect());
                invalidate();
            }
        };

    private final Runnable mStopMovingRunnbale = new Runnable()
        {
            @Override
            public void run()
            {
                if (isMove)
                {
                    mLeftIndicator.set(mMovingLeftIndicator);
                    mRightIndicator.set(mMovingRightIndicator);
                    mLeftShadow.set(mRect.left, mRect.top, mLeftIndicator.left, mRect.bottom);
                    mRightShadow.set(mRightIndicator.right, mRect.top, mRect.right, mRect.bottom);
                }
                else
                {
                    mMovingLeftIndicator.set(mLeftIndicator);
                    mMovingRightIndicator.set(mRightIndicator);
                }
                isLeft = false;
                isRight = false;
                isCenter = false;
                isMove = false;

                mListener.onIndicatorChanged(calcCenterRect());
                invalidate();
            }
        };

    public ChartIndicatorUtil( final Context context, final IndicatorChartView indicatorChartView, final AbstractSeries series )
    {
        mContext = context;
        mIndicator = indicatorChartView;
        mSeries = series;

        initRect();
        initPaint();
    }

    private void initRect()
    {
        mRect = mIndicator.getContentRect();
        mLeftIndicator.set(mRect.left, mRect.top, mRect.left + INDICATOR_WIDTH, mRect.bottom);
        mRightIndicator.set(mRect.right - INDICATOR_WIDTH, mRect.top, mRect.right, mRect.bottom);
        mMovingLeftIndicator.set(mLeftIndicator);
        mMovingRightIndicator.set(mRightIndicator);

        limit = (float) (mRect.width() * mIndicator.getChartView().AXIS_X_LIMIT_PRECENT);
    }

    private void initPaint()
    {
        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        indicatorPaint.setStrokeWidth(2);
        indicatorPaint.setColor(Color.GRAY);
        indicatorPaint.setAlpha((int) (255 * 0.5));

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(mContext.getResources().getColor(R.color.indicator_shadow));
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAlpha((int) (255 * 0.8));
    }

    public void updateIndicator( RectF mChartViewprot )
    {
        float scale = mRect.width() / mIndicator.getCurrentViewport().width();
        float left = (mChartViewprot.left - mIndicator.getCurrentViewport().left) * scale;
        float right = (mChartViewprot.right - mIndicator.getCurrentViewport().left) * scale;

        if (left <= INDICATOR_WIDTH)
        {
            left = INDICATOR_WIDTH;
            right = left + mChartViewprot.width() * scale;
        }
        if (right >= mRect.right - INDICATOR_WIDTH)
        {
            right = mRect.right - INDICATOR_WIDTH;
            left = right - mChartViewprot.width() * scale;
            if (left <= INDICATOR_WIDTH)
            {
                left = INDICATOR_WIDTH;
            }
        }
        mLeftIndicator.set(left - INDICATOR_WIDTH, mRect.top, left, mRect.bottom);
        mRightIndicator.set(right, mRect.top, right + INDICATOR_WIDTH, mRect.bottom);
        mLeftShadow.set(mRect.left, mRect.top, mLeftIndicator.left, mRect.bottom);
        mRightShadow.set(mRightIndicator.right, mRect.top, mRect.right, mRect.bottom);
        mMovingLeftIndicator.set(mLeftIndicator);
        mMovingRightIndicator.set(mRightIndicator);

        invalidate();
    }

    public RectF calcCenterRect()
    {
        float scale = mIndicator.getCurrentViewport().width() / mRect.width();

        float left = mIndicator.getCurrentViewport().left + mLeftIndicator.right * scale;
        float right = mIndicator.getCurrentViewport().left + mRightIndicator.left * scale;

        return new RectF(left, mIndicator.getCurrentViewport().top, right, mIndicator.getCurrentViewport().bottom);
    }

    public AbstractSeries getSeries()
    {
        return mSeries;
    }

    /** Handle IndicatorChartView touch event */
    public boolean handleIndicator( final MotionEvent event )
    {
        mCurrentX = (int) event.getX();
        mCurrentY = mRect.centerY();
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
        case MotionEvent.ACTION_DOWN:
            mHandler.removeCallbacks(mStopMovingRunnbale);
            // out of touch scope
            if (!mRect.contains(mCurrentX, mCurrentY))
            {
                return false;
            }
            // left indicator touched
            if (mCurrentX < mLeftIndicator.right)
            {
                isLeft = true;
            }
            // right indicator touched
            else if (mCurrentX > mRightIndicator.left)
            {
                isRight = true;
            }
            // center indicator touched
            else
            {
                isCenter = true;
                indicatorPaddingLeft = mCurrentX - mLeftIndicator.left;
                indicatorPaddingRight = mRightIndicator.right - mCurrentX;
            }
            break;
        case MotionEvent.ACTION_MOVE:
            // left indicator move
            if (isLeft)
            {
                mHandler.post(mMoveLeftRunnbale);
            }
            // right indicator move
            else if (isRight)
            {
                mHandler.post(mMoveRightRunnbale);
            }
            // center indicator move
            else if (isCenter)
            {
                mHandler.post(mMoveCenterRunnbale);
            }
            break;
        default:
            mHandler.post(mStopMovingRunnbale);
            mHandler.removeCallbacks(mMoveLeftRunnbale);
            mHandler.removeCallbacks(mMoveRightRunnbale);
            mHandler.removeCallbacks(mMoveCenterRunnbale);
            break;
        }

        return true;
    }

    public void setListener( final OnIndicatorListener listener )
    {
        mListener = listener;
    }

    public void setSeries( final AbstractSeries series )
    {
        this.mSeries = series;
    }

    public void drawIndicator( Canvas canvas )
    {
        canvas.drawRect(mLeftShadow, shadowPaint);
        canvas.drawRect(mRightShadow, shadowPaint);

        canvas.drawRect(mLeftIndicator, indicatorPaint);
        canvas.drawRect(mRightIndicator, indicatorPaint);

        if (isMove)
        {
            canvas.drawRect(mMovingLeftIndicator, indicatorPaint);
            canvas.drawRect(mMovingRightIndicator, indicatorPaint);
        }
    }

    private void invalidate()
    {
        mIndicator.invalidate();
    }

    public Rect getRect()
    {
        return mRect;
    }

    public void setRect( Rect mRect )
    {
        this.mRect = mRect;
    }
}
