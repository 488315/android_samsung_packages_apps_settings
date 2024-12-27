package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.jobs.MoveViewJob;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.ChartTouchListener$ChartGesture;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsWeeklyBarChart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BarLineChartBase extends ViewGroup
        implements BarLineScatterCandleBubbleDataProvider {
    public ChartAnimator mAnimator;
    public boolean mAutoScaleMinMaxEnabled;
    public YAxis mAxisLeft;
    public YAxisRenderer mAxisRendererLeft;
    public YAxisRenderer mAxisRendererRight;
    public YAxis mAxisRight;
    public Paint mBorderPaint;
    public BarLineChartTouchListener mChartTouchListener;
    public final boolean mClipDataToContent;
    public BarLineScatterCandleBubbleData mData;
    public final DefaultValueFormatter mDefaultValueFormatter;
    public Paint mDescPaint;
    public Description mDescription;
    public boolean mDoubleTapToZoomEnabled;
    public boolean mDragDecelerationEnabled;
    public float mDragDecelerationFrictionCoef;
    public boolean mDragXEnabled;
    public boolean mDragYEnabled;
    public boolean mDrawGridBackground;
    public boolean mDrawMarkers;
    public float mExtraBottomOffset;
    public float mExtraLeftOffset;
    public float mExtraRightOffset;
    public float mExtraTopOffset;
    public final Matrix mFitScreenMatrixBuffer;
    public WifiApClientsWeeklyBarChart.AnonymousClass1 mGestureListener;
    public Paint mGridBackgroundPaint;
    public final boolean mHighLightPerTapEnabled;
    public final boolean mHighlightPerDragEnabled;
    public ChartHighlighter mHighlighter;
    public Highlight[] mIndicesToHighlight;
    public Paint mInfoPaint;
    public final ArrayList mJobs;
    public Transformer mLeftAxisTransformer;
    public Legend mLegend;
    public LegendRenderer mLegendRenderer;
    public IMarker mMarker;
    public float mMaxHighlightDistance;
    public final int mMaxVisibleCount;
    public final float mMinOffset;
    public final String mNoDataText;
    public final RectF mOffsetsBuffer;
    public boolean mOffsetsCalculated;
    public final float[] mOnSizeChangedBuffer;
    public BarLineScatterCandleBubbleRenderer mRenderer;
    public Transformer mRightAxisTransformer;
    public boolean mScaleXEnabled;
    public boolean mScaleYEnabled;
    public OnChartValueSelectedListener mSelectionListener;
    public boolean mTouchEnabled;
    public final ViewPortHandler mViewPortHandler;
    public XAxis mXAxis;
    public XAxisRenderer mXAxisRenderer;
    public final Matrix mZoomMatrixBuffer;
    public final MPPointD posForGetHighestVisibleX;
    public final MPPointD posForGetLowestVisibleX;

    public BarLineChartBase(Context context) {
        super(context);
        this.mData = null;
        this.mHighLightPerTapEnabled = true;
        this.mDragDecelerationEnabled = true;
        this.mDragDecelerationFrictionCoef = 0.9f;
        this.mDefaultValueFormatter = new DefaultValueFormatter(0);
        this.mTouchEnabled = true;
        this.mNoDataText = "No chart data available.";
        this.mViewPortHandler = new ViewPortHandler();
        this.mExtraTopOffset = 0.0f;
        this.mExtraRightOffset = 0.0f;
        this.mExtraBottomOffset = 0.0f;
        this.mExtraLeftOffset = 0.0f;
        this.mOffsetsCalculated = false;
        this.mMaxHighlightDistance = 0.0f;
        this.mDrawMarkers = true;
        this.mJobs = new ArrayList();
        init();
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mClipDataToContent = true;
        this.mMinOffset = 15.0f;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public final void animateY() {
        ChartAnimator chartAnimator = this.mAnimator;
        chartAnimator.getClass();
        Easing.AnonymousClass1 anonymousClass1 = Easing.Linear;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(chartAnimator, "phaseY", 0.0f, 1.0f);
        ofFloat.setInterpolator(anonymousClass1);
        ofFloat.setDuration(750);
        ofFloat.addUpdateListener(chartAnimator.mListener);
        ofFloat.start();
    }

    public void calcMinMax() {
        XAxis xAxis = this.mXAxis;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.mData;
        xAxis.calculate(barLineScatterCandleBubbleData.mXMin, barLineScatterCandleBubbleData.mXMax);
        YAxis yAxis = this.mAxisLeft;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData2 = this.mData;
        YAxis.AxisDependency axisDependency = YAxis.AxisDependency.LEFT;
        yAxis.calculate(
                barLineScatterCandleBubbleData2.getYMin(axisDependency),
                this.mData.getYMax(axisDependency));
        YAxis yAxis2 = this.mAxisRight;
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData3 = this.mData;
        YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.RIGHT;
        yAxis2.calculate(
                barLineScatterCandleBubbleData3.getYMin(axisDependency2),
                this.mData.getYMax(axisDependency2));
    }

    public final void calculateOffsets() {
        RectF rectF = this.mOffsetsBuffer;
        rectF.left = 0.0f;
        rectF.right = 0.0f;
        rectF.top = 0.0f;
        rectF.bottom = 0.0f;
        Legend legend = this.mLegend;
        if (legend != null && legend.mEnabled) {
            int ordinal = legend.mOrientation.ordinal();
            if (ordinal == 0) {
                int ordinal2 = this.mLegend.mVerticalAlignment.ordinal();
                if (ordinal2 == 0) {
                    float f = rectF.top;
                    Legend legend2 = this.mLegend;
                    rectF.top =
                            Math.min(
                                            legend2.mNeededHeight,
                                            this.mViewPortHandler.mChartHeight
                                                    * legend2.mMaxSizePercent)
                                    + this.mLegend.mYOffset
                                    + f;
                } else if (ordinal2 == 2) {
                    float f2 = rectF.bottom;
                    Legend legend3 = this.mLegend;
                    rectF.bottom =
                            Math.min(
                                            legend3.mNeededHeight,
                                            this.mViewPortHandler.mChartHeight
                                                    * legend3.mMaxSizePercent)
                                    + this.mLegend.mYOffset
                                    + f2;
                }
            } else if (ordinal == 1) {
                int ordinal3 = this.mLegend.mHorizontalAlignment.ordinal();
                if (ordinal3 == 0) {
                    float f3 = rectF.left;
                    Legend legend4 = this.mLegend;
                    rectF.left =
                            Math.min(
                                            legend4.mNeededWidth,
                                            this.mViewPortHandler.mChartWidth
                                                    * legend4.mMaxSizePercent)
                                    + this.mLegend.mXOffset
                                    + f3;
                } else if (ordinal3 == 1) {
                    int ordinal4 = this.mLegend.mVerticalAlignment.ordinal();
                    if (ordinal4 == 0) {
                        float f4 = rectF.top;
                        Legend legend5 = this.mLegend;
                        rectF.top =
                                Math.min(
                                                legend5.mNeededHeight,
                                                this.mViewPortHandler.mChartHeight
                                                        * legend5.mMaxSizePercent)
                                        + this.mLegend.mYOffset
                                        + f4;
                    } else if (ordinal4 == 2) {
                        float f5 = rectF.bottom;
                        Legend legend6 = this.mLegend;
                        rectF.bottom =
                                Math.min(
                                                legend6.mNeededHeight,
                                                this.mViewPortHandler.mChartHeight
                                                        * legend6.mMaxSizePercent)
                                        + this.mLegend.mYOffset
                                        + f5;
                    }
                } else if (ordinal3 == 2) {
                    float f6 = rectF.right;
                    Legend legend7 = this.mLegend;
                    rectF.right =
                            Math.min(
                                            legend7.mNeededWidth,
                                            this.mViewPortHandler.mChartWidth
                                                    * legend7.mMaxSizePercent)
                                    + this.mLegend.mXOffset
                                    + f6;
                }
            }
        }
        RectF rectF2 = this.mOffsetsBuffer;
        float f7 = rectF2.left + 0.0f;
        float f8 = rectF2.top + 0.0f;
        float f9 = rectF2.right + 0.0f;
        float f10 = rectF2.bottom + 0.0f;
        YAxis yAxis = this.mAxisLeft;
        boolean z = yAxis.mEnabled;
        YAxis.YAxisLabelPosition yAxisLabelPosition = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
        if (z && yAxis.mDrawLabels && yAxis.mPosition == yAxisLabelPosition) {
            f7 += yAxis.getRequiredWidthSpace(this.mAxisRendererLeft.mAxisLabelPaint);
        }
        YAxis yAxis2 = this.mAxisRight;
        if (yAxis2.mEnabled && yAxis2.mDrawLabels && yAxis2.mPosition == yAxisLabelPosition) {
            f9 += yAxis2.getRequiredWidthSpace(this.mAxisRendererRight.mAxisLabelPaint);
        }
        XAxis xAxis = this.mXAxis;
        if (xAxis.mEnabled && xAxis.mDrawLabels) {
            float f11 = xAxis.mLabelRotatedHeight + xAxis.mYOffset;
            XAxis.XAxisPosition xAxisPosition = xAxis.mPosition;
            if (xAxisPosition == XAxis.XAxisPosition.BOTTOM) {
                f10 += f11;
            } else {
                if (xAxisPosition != XAxis.XAxisPosition.TOP) {
                    if (xAxisPosition == XAxis.XAxisPosition.BOTH_SIDED) {
                        f10 += f11;
                    }
                }
                f8 += f11;
            }
        }
        float f12 = f8 + this.mExtraTopOffset;
        float f13 = f9 + this.mExtraRightOffset;
        float f14 = f10 + this.mExtraBottomOffset;
        float f15 = f7 + this.mExtraLeftOffset;
        float convertDpToPixel = Utils.convertDpToPixel(this.mMinOffset);
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        viewPortHandler.mContentRect.set(
                Math.max(convertDpToPixel, f15),
                Math.max(convertDpToPixel, f12),
                viewPortHandler.mChartWidth - Math.max(convertDpToPixel, f13),
                viewPortHandler.mChartHeight - Math.max(convertDpToPixel, f14));
        Transformer transformer = this.mRightAxisTransformer;
        this.mAxisRight.getClass();
        transformer.prepareMatrixOffset();
        Transformer transformer2 = this.mLeftAxisTransformer;
        this.mAxisLeft.getClass();
        transformer2.prepareMatrixOffset();
        Transformer transformer3 = this.mRightAxisTransformer;
        XAxis xAxis2 = this.mXAxis;
        float f16 = xAxis2.mAxisMinimum;
        float f17 = xAxis2.mAxisRange;
        YAxis yAxis3 = this.mAxisRight;
        transformer3.prepareMatrixValuePx(f16, f17, yAxis3.mAxisRange, yAxis3.mAxisMinimum);
        Transformer transformer4 = this.mLeftAxisTransformer;
        XAxis xAxis3 = this.mXAxis;
        float f18 = xAxis3.mAxisMinimum;
        float f19 = xAxis3.mAxisRange;
        YAxis yAxis4 = this.mAxisLeft;
        transformer4.prepareMatrixValuePx(f18, f19, yAxis4.mAxisRange, yAxis4.mAxisMinimum);
    }

    @Override // android.view.View
    public final void computeScroll() {
        BarLineChartTouchListener barLineChartTouchListener = this.mChartTouchListener;
        if (barLineChartTouchListener instanceof BarLineChartTouchListener) {
            MPPointF mPPointF = barLineChartTouchListener.mDecelerationVelocity;
            if (mPPointF.x == 0.0f && mPPointF.y == 0.0f) {
                return;
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            MPPointF mPPointF2 = barLineChartTouchListener.mDecelerationVelocity;
            float f = mPPointF2.x;
            float f2 = barLineChartTouchListener.mChart.mDragDecelerationFrictionCoef;
            float f3 = f * f2;
            mPPointF2.x = f3;
            float f4 = mPPointF2.y * f2;
            mPPointF2.y = f4;
            float f5 =
                    (currentAnimationTimeMillis - barLineChartTouchListener.mDecelerationLastTime)
                            / 1000.0f;
            float f6 = f3 * f5;
            float f7 = f4 * f5;
            MPPointF mPPointF3 = barLineChartTouchListener.mDecelerationCurrentPoint;
            float f8 = mPPointF3.x + f6;
            mPPointF3.x = f8;
            float f9 = mPPointF3.y + f7;
            mPPointF3.y = f9;
            MotionEvent obtain =
                    MotionEvent.obtain(
                            currentAnimationTimeMillis, currentAnimationTimeMillis, 2, f8, f9, 0);
            BarLineChartBase barLineChartBase = barLineChartTouchListener.mChart;
            barLineChartTouchListener.performDrag(
                    barLineChartBase.mDragXEnabled
                            ? barLineChartTouchListener.mDecelerationCurrentPoint.x
                                    - barLineChartTouchListener.mTouchStartPoint.x
                            : 0.0f,
                    barLineChartBase.mDragYEnabled
                            ? barLineChartTouchListener.mDecelerationCurrentPoint.y
                                    - barLineChartTouchListener.mTouchStartPoint.y
                            : 0.0f);
            obtain.recycle();
            BarLineChartBase barLineChartBase2 = barLineChartTouchListener.mChart;
            ViewPortHandler viewPortHandler = barLineChartBase2.mViewPortHandler;
            Matrix matrix = barLineChartTouchListener.mMatrix;
            viewPortHandler.refresh(matrix, barLineChartBase2, false);
            barLineChartTouchListener.mMatrix = matrix;
            barLineChartTouchListener.mDecelerationLastTime = currentAnimationTimeMillis;
            if (Math.abs(barLineChartTouchListener.mDecelerationVelocity.x) >= 0.01d
                    || Math.abs(barLineChartTouchListener.mDecelerationVelocity.y) >= 0.01d) {
                BarLineChartBase barLineChartBase3 = barLineChartTouchListener.mChart;
                DisplayMetrics displayMetrics = Utils.mMetrics;
                barLineChartBase3.postInvalidateOnAnimation();
            } else {
                barLineChartTouchListener.mChart.calculateOffsets();
                barLineChartTouchListener.mChart.postInvalidate();
                MPPointF mPPointF4 = barLineChartTouchListener.mDecelerationVelocity;
                mPPointF4.x = 0.0f;
                mPPointF4.y = 0.0f;
            }
        }
    }

    @Override // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public final float getHighestVisibleX() {
        Transformer transformer = getTransformer(YAxis.AxisDependency.LEFT);
        RectF rectF = this.mViewPortHandler.mContentRect;
        transformer.getValuesByTouchPoint(rectF.right, rectF.bottom, this.posForGetHighestVisibleX);
        return (float) Math.min(this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.x);
    }

    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData != null) {
            return this.mHighlighter.getHighlight(f, f2);
        }
        Log.e("MPAndroidChart", "Can't select by touch. No data set.");
        return null;
    }

    public final float getLowestVisibleX() {
        Transformer transformer = getTransformer(YAxis.AxisDependency.LEFT);
        RectF rectF = this.mViewPortHandler.mContentRect;
        transformer.getValuesByTouchPoint(rectF.left, rectF.bottom, this.posForGetLowestVisibleX);
        return (float) Math.max(this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.x);
    }

    @Override // android.view.View
    public final float getScaleX() {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler == null) {
            return 1.0f;
        }
        return viewPortHandler.mScaleX;
    }

    @Override // android.view.View
    public final float getScaleY() {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        if (viewPortHandler == null) {
            return 1.0f;
        }
        return viewPortHandler.mScaleY;
    }

    public final Transformer getTransformer(YAxis.AxisDependency axisDependency) {
        return axisDependency == YAxis.AxisDependency.LEFT
                ? this.mLeftAxisTransformer
                : this.mRightAxisTransformer;
    }

    public final void highlightValue() {
        highlightValue(null, false);
    }

    public void init() {
        setWillNotDraw(false);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.github.mikephil.charting.charts.Chart$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BarLineChartBase.this.postInvalidate();
                    }
                };
        ChartAnimator chartAnimator = new ChartAnimator();
        chartAnimator.mListener = animatorUpdateListener;
        this.mAnimator = chartAnimator;
        Context context = getContext();
        DisplayMetrics displayMetrics = Utils.mMetrics;
        if (context == null) {
            Utils.mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            Utils.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
        } else {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            Utils.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            Utils.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            Utils.mMetrics = context.getResources().getDisplayMetrics();
        }
        this.mMaxHighlightDistance = Utils.convertDpToPixel(500.0f);
        Description description = new Description();
        description.text = "Description Label";
        description.mTextAlign = Paint.Align.RIGHT;
        description.mTextSize = Utils.convertDpToPixel(8.0f);
        this.mDescription = description;
        Legend legend = new Legend();
        legend.mEntries = new LegendEntry[0];
        legend.mIsLegendCustom = false;
        legend.mHorizontalAlignment = Legend.LegendHorizontalAlignment.LEFT;
        legend.mVerticalAlignment = Legend.LegendVerticalAlignment.BOTTOM;
        legend.mOrientation = Legend.LegendOrientation.HORIZONTAL;
        legend.mDirection = Legend.LegendDirection.LEFT_TO_RIGHT;
        legend.mShape = Legend.LegendForm.SQUARE;
        legend.mFormSize = 8.0f;
        legend.mFormLineWidth = 3.0f;
        legend.mXEntrySpace = 6.0f;
        legend.mFormToTextSpace = 5.0f;
        legend.mStackSpace = 3.0f;
        legend.mMaxSizePercent = 0.95f;
        legend.mNeededWidth = 0.0f;
        legend.mNeededHeight = 0.0f;
        legend.mCalculatedLabelSizes = new ArrayList(16);
        legend.mCalculatedLabelBreakPoints = new ArrayList(16);
        legend.mCalculatedLineSizes = new ArrayList(16);
        legend.mTextSize = Utils.convertDpToPixel(10.0f);
        legend.mXOffset = Utils.convertDpToPixel(5.0f);
        legend.mYOffset = Utils.convertDpToPixel(3.0f);
        this.mLegend = legend;
        LegendRenderer legendRenderer = new LegendRenderer(this.mViewPortHandler);
        legendRenderer.computedEntries = new ArrayList(16);
        legendRenderer.legendFontMetrics = new Paint.FontMetrics();
        legendRenderer.mLineFormPath = new Path();
        legendRenderer.mLegend = legend;
        Paint paint = new Paint(1);
        legendRenderer.mLegendLabelPaint = paint;
        paint.setTextSize(Utils.convertDpToPixel(9.0f));
        paint.setTextAlign(Paint.Align.LEFT);
        Paint paint2 = new Paint(1);
        legendRenderer.mLegendFormPaint = paint2;
        Paint.Style style = Paint.Style.FILL;
        paint2.setStyle(style);
        this.mLegendRenderer = legendRenderer;
        XAxis xAxis = new XAxis();
        xAxis.mLabelRotatedHeight = 1;
        xAxis.mAvoidFirstLastClipping = false;
        xAxis.mPosition = XAxis.XAxisPosition.TOP;
        xAxis.mYOffset = Utils.convertDpToPixel(4.0f);
        this.mXAxis = xAxis;
        this.mDescPaint = new Paint(1);
        Paint paint3 = new Paint(1);
        this.mInfoPaint = paint3;
        paint3.setColor(Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut, 189, 51));
        this.mInfoPaint.setTextAlign(Paint.Align.CENTER);
        this.mInfoPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mAxisLeft = new YAxis(YAxis.AxisDependency.LEFT);
        this.mAxisRight = new YAxis(YAxis.AxisDependency.RIGHT);
        this.mLeftAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mRightAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mAxisRendererLeft =
                new YAxisRenderer(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight =
                new YAxisRenderer(
                        this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer =
                new XAxisRenderer(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer);
        this.mHighlighter = new ChartHighlighter(this);
        Matrix matrix = this.mViewPortHandler.mMatrixTouch;
        BarLineChartTouchListener barLineChartTouchListener = new BarLineChartTouchListener();
        barLineChartTouchListener.mLastGesture = ChartTouchListener$ChartGesture.NONE;
        barLineChartTouchListener.mTouchMode = 0;
        barLineChartTouchListener.mChart = this;
        barLineChartTouchListener.mGestureDetector =
                new GestureDetector(getContext(), barLineChartTouchListener);
        barLineChartTouchListener.mMatrix = new Matrix();
        barLineChartTouchListener.mSavedMatrix = new Matrix();
        barLineChartTouchListener.mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);
        barLineChartTouchListener.mTouchPointCenter = MPPointF.getInstance(0.0f, 0.0f);
        barLineChartTouchListener.mSavedXDist = 1.0f;
        barLineChartTouchListener.mSavedYDist = 1.0f;
        barLineChartTouchListener.mSavedDist = 1.0f;
        barLineChartTouchListener.mDecelerationLastTime = 0L;
        barLineChartTouchListener.mDecelerationCurrentPoint = MPPointF.getInstance(0.0f, 0.0f);
        barLineChartTouchListener.mDecelerationVelocity = MPPointF.getInstance(0.0f, 0.0f);
        barLineChartTouchListener.mMatrix = matrix;
        barLineChartTouchListener.mDragTriggerDist = Utils.convertDpToPixel(3.0f);
        barLineChartTouchListener.mMinScalePointerDistance = Utils.convertDpToPixel(3.5f);
        this.mChartTouchListener = barLineChartTouchListener;
        Paint paint4 = new Paint();
        this.mGridBackgroundPaint = paint4;
        paint4.setStyle(style);
        this.mGridBackgroundPaint.setColor(
                Color.rgb(
                        IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
                        IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp,
                        IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp));
        Paint paint5 = new Paint();
        this.mBorderPaint = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(EmergencyPhoneWidget.BG_COLOR);
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
    }

    public final void isInverted(YAxis.AxisDependency axisDependency) {
        (axisDependency == YAxis.AxisDependency.LEFT ? this.mAxisLeft : this.mAxisRight).getClass();
    }

    public final void moveViewToX(float f) {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        Transformer transformer = getTransformer(YAxis.AxisDependency.LEFT);
        MoveViewJob moveViewJob = (MoveViewJob) MoveViewJob.pool.get();
        moveViewJob.mViewPortHandler = viewPortHandler;
        moveViewJob.xValue = f;
        moveViewJob.yValue = 0.0f;
        moveViewJob.mTrans = transformer;
        moveViewJob.view = this;
        ViewPortHandler viewPortHandler2 = this.mViewPortHandler;
        if (viewPortHandler2.mChartHeight <= 0.0f || viewPortHandler2.mChartWidth <= 0.0f) {
            this.mJobs.add(moveViewJob);
        } else {
            post(moveViewJob);
        }
    }

    public final void notifyDataSetChanged() {
        Paint paint;
        int i;
        float f;
        String str;
        if (this.mData == null) {
            return;
        }
        BarLineScatterCandleBubbleRenderer barLineScatterCandleBubbleRenderer = this.mRenderer;
        if (barLineScatterCandleBubbleRenderer != null) {
            barLineScatterCandleBubbleRenderer.initBuffers();
        }
        calcMinMax();
        YAxisRenderer yAxisRenderer = this.mAxisRendererLeft;
        YAxis yAxis = this.mAxisLeft;
        yAxisRenderer.computeAxis(yAxis.mAxisMinimum, yAxis.mAxisMaximum);
        YAxisRenderer yAxisRenderer2 = this.mAxisRendererRight;
        YAxis yAxis2 = this.mAxisRight;
        yAxisRenderer2.computeAxis(yAxis2.mAxisMinimum, yAxis2.mAxisMaximum);
        XAxisRenderer xAxisRenderer = this.mXAxisRenderer;
        XAxis xAxis = this.mXAxis;
        xAxisRenderer.computeAxis(xAxis.mAxisMinimum, xAxis.mAxisMaximum);
        if (this.mLegend != null) {
            LegendRenderer legendRenderer = this.mLegendRenderer;
            BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.mData;
            Legend legend = legendRenderer.mLegend;
            boolean z = legend.mIsLegendCustom;
            Legend.LegendForm legendForm = Legend.LegendForm.NONE;
            int i2 = 1;
            if (!z) {
                ((ArrayList) legendRenderer.computedEntries).clear();
                int i3 = 0;
                while (i3 < barLineScatterCandleBubbleData.getDataSetCount()) {
                    BarLineScatterCandleBubbleDataSet dataSetByIndex =
                            barLineScatterCandleBubbleData.getDataSetByIndex(i3);
                    if (dataSetByIndex != null) {
                        List list = dataSetByIndex.mColors;
                        int size = dataSetByIndex.mEntries.size();
                        if (dataSetByIndex instanceof BarDataSet) {
                            BarDataSet barDataSet = (BarDataSet) dataSetByIndex;
                            if (barDataSet.mStackSize > i2) {
                                int min = Math.min(list.size(), barDataSet.mStackSize);
                                for (int i4 = 0; i4 < min; i4++) {
                                    String[] strArr = barDataSet.mStackLabels;
                                    if (strArr.length > 0) {
                                        int i5 = i4 % min;
                                        str = i5 < strArr.length ? strArr[i5] : null;
                                    } else {
                                        str = null;
                                    }
                                    ((ArrayList) legendRenderer.computedEntries)
                                            .add(
                                                    new LegendEntry(
                                                            str,
                                                            dataSetByIndex.mForm,
                                                            dataSetByIndex.mFormSize,
                                                            dataSetByIndex.mFormLineWidth,
                                                            ((Integer) list.get(i4)).intValue()));
                                }
                                if (barDataSet.mLabel != null) {
                                    ((ArrayList) legendRenderer.computedEntries)
                                            .add(
                                                    new LegendEntry(
                                                            dataSetByIndex.mLabel,
                                                            legendForm,
                                                            Float.NaN,
                                                            Float.NaN,
                                                            1122867));
                                }
                            }
                        }
                        int i6 = 0;
                        while (i6 < list.size() && i6 < size) {
                            ((ArrayList) legendRenderer.computedEntries)
                                    .add(
                                            new LegendEntry(
                                                    (i6 >= list.size() - 1 || i6 >= size + (-1))
                                                            ? barLineScatterCandleBubbleData
                                                                    .getDataSetByIndex(i3)
                                                                    .mLabel
                                                            : null,
                                                    dataSetByIndex.mForm,
                                                    dataSetByIndex.mFormSize,
                                                    dataSetByIndex.mFormLineWidth,
                                                    ((Integer) list.get(i6)).intValue()));
                            i6++;
                        }
                    }
                    i3++;
                    i2 = 1;
                }
                ArrayList arrayList = (ArrayList) legendRenderer.computedEntries;
                legend.mEntries =
                        (LegendEntry[]) arrayList.toArray(new LegendEntry[arrayList.size()]);
            }
            Typeface typeface = legend.mTypeface;
            if (typeface != null) {
                legendRenderer.mLegendLabelPaint.setTypeface(typeface);
            }
            legendRenderer.mLegendLabelPaint.setTextSize(legend.mTextSize);
            legendRenderer.mLegendLabelPaint.setColor(legend.mTextColor);
            Paint paint2 = legendRenderer.mLegendLabelPaint;
            float f2 = legend.mFormSize;
            float convertDpToPixel = Utils.convertDpToPixel(f2);
            float convertDpToPixel2 = Utils.convertDpToPixel(legend.mStackSpace);
            float f3 = legend.mFormToTextSpace;
            float convertDpToPixel3 = Utils.convertDpToPixel(f3);
            float convertDpToPixel4 = Utils.convertDpToPixel(legend.mXEntrySpace);
            float convertDpToPixel5 = Utils.convertDpToPixel(0.0f);
            LegendEntry[] legendEntryArr = legend.mEntries;
            int length = legendEntryArr.length;
            Utils.convertDpToPixel(f3);
            LegendEntry[] legendEntryArr2 = legend.mEntries;
            int length2 = legendEntryArr2.length;
            float f4 = 0.0f;
            float f5 = 0.0f;
            int i7 = 0;
            while (i7 < length2) {
                LegendEntry legendEntry = legendEntryArr2[i7];
                float f6 = f2;
                float convertDpToPixel6 =
                        Utils.convertDpToPixel(
                                Float.isNaN(legendEntry.formSize) ? f6 : legendEntry.formSize);
                if (convertDpToPixel6 > f5) {
                    f5 = convertDpToPixel6;
                }
                String str2 = legendEntry.label;
                if (str2 != null) {
                    float measureText = (int) paint2.measureText(str2);
                    if (measureText > f4) {
                        f4 = measureText;
                    }
                }
                i7++;
                f2 = f6;
            }
            float f7 = 0.0f;
            for (LegendEntry legendEntry2 : legend.mEntries) {
                String str3 = legendEntry2.label;
                if (str3 != null) {
                    float calcTextHeight = Utils.calcTextHeight(paint2, str3);
                    if (calcTextHeight > f7) {
                        f7 = calcTextHeight;
                    }
                }
            }
            int ordinal = legend.mOrientation.ordinal();
            if (ordinal == 0) {
                Paint.FontMetrics fontMetrics = Utils.mFontMetrics;
                paint2.getFontMetrics(fontMetrics);
                float f8 = fontMetrics.descent - fontMetrics.ascent;
                paint2.getFontMetrics(fontMetrics);
                float f9 =
                        (fontMetrics.ascent - fontMetrics.top)
                                + fontMetrics.bottom
                                + convertDpToPixel5;
                legendRenderer.mViewPortHandler.mContentRect.width();
                ((ArrayList) legend.mCalculatedLabelBreakPoints).clear();
                ((ArrayList) legend.mCalculatedLabelSizes).clear();
                ((ArrayList) legend.mCalculatedLineSizes).clear();
                float f10 = 0.0f;
                int i8 = 0;
                float f11 = 0.0f;
                int i9 = -1;
                float f12 = 0.0f;
                while (i8 < length) {
                    LegendEntry legendEntry3 = legendEntryArr[i8];
                    float f13 = convertDpToPixel4;
                    boolean z2 = legendEntry3.form != legendForm;
                    float f14 = legendEntry3.formSize;
                    float convertDpToPixel7 =
                            Float.isNaN(f14) ? convertDpToPixel : Utils.convertDpToPixel(f14);
                    String str4 = legendEntry3.label;
                    LegendEntry[] legendEntryArr3 = legendEntryArr;
                    float f15 = f9;
                    ((ArrayList) legend.mCalculatedLabelBreakPoints).add(Boolean.FALSE);
                    float f16 = i9 == -1 ? 0.0f : f10 + convertDpToPixel2;
                    if (str4 != null) {
                        ((ArrayList) legend.mCalculatedLabelSizes)
                                .add(Utils.calcTextSize(paint2, str4));
                        f10 =
                                f16
                                        + (z2 ? convertDpToPixel3 + convertDpToPixel7 : 0.0f)
                                        + ((FSize)
                                                        ((ArrayList) legend.mCalculatedLabelSizes)
                                                                .get(i8))
                                                .width;
                        paint = paint2;
                        i = -1;
                    } else {
                        List list2 = legend.mCalculatedLabelSizes;
                        FSize fSize = (FSize) FSize.pool.get();
                        paint = paint2;
                        fSize.width = 0.0f;
                        fSize.height = 0.0f;
                        ((ArrayList) list2).add(fSize);
                        if (!z2) {
                            convertDpToPixel7 = 0.0f;
                        }
                        f10 = f16 + convertDpToPixel7;
                        i = -1;
                        if (i9 == -1) {
                            i9 = i8;
                        }
                    }
                    if (str4 != null || i8 == length - 1) {
                        f12 += (f12 == 0.0f ? 0.0f : f13) + f10;
                        if (i8 == length - 1) {
                            List list3 = legend.mCalculatedLineSizes;
                            FSize fSize2 = (FSize) FSize.pool.get();
                            fSize2.width = f12;
                            fSize2.height = f8;
                            ((ArrayList) list3).add(fSize2);
                            f11 = Math.max(f11, f12);
                        }
                    }
                    if (str4 != null) {
                        i9 = i;
                    }
                    i8++;
                    convertDpToPixel4 = f13;
                    legendEntryArr = legendEntryArr3;
                    f9 = f15;
                    paint2 = paint;
                }
                float f17 = f9;
                legend.mNeededWidth = f11;
                legend.mNeededHeight =
                        (f17
                                        * (((ArrayList) legend.mCalculatedLineSizes).size() == 0
                                                ? 0
                                                : ((ArrayList) legend.mCalculatedLineSizes).size()
                                                        - 1))
                                + (f8 * ((ArrayList) legend.mCalculatedLineSizes).size());
            } else if (ordinal == 1) {
                Paint.FontMetrics fontMetrics2 = Utils.mFontMetrics;
                paint2.getFontMetrics(fontMetrics2);
                float f18 = fontMetrics2.descent - fontMetrics2.ascent;
                float f19 = 0.0f;
                float f20 = 0.0f;
                float f21 = 0.0f;
                int i10 = 0;
                boolean z3 = false;
                while (i10 < length) {
                    LegendEntry legendEntry4 = legendEntryArr[i10];
                    float f22 = convertDpToPixel;
                    float f23 = f21;
                    boolean z4 = legendEntry4.form != legendForm;
                    float f24 = legendEntry4.formSize;
                    float convertDpToPixel8 = Float.isNaN(f24) ? f22 : Utils.convertDpToPixel(f24);
                    String str5 = legendEntry4.label;
                    if (!z3) {
                        f23 = 0.0f;
                    }
                    if (z4) {
                        if (z3) {
                            f23 += convertDpToPixel2;
                        }
                        f23 += convertDpToPixel8;
                    }
                    Legend.LegendForm legendForm2 = legendForm;
                    float f25 = f23;
                    if (str5 != null) {
                        if (z4 && !z3) {
                            f = f25 + convertDpToPixel3;
                        } else if (z3) {
                            f19 = Math.max(f19, f25);
                            f20 += f18 + convertDpToPixel5;
                            f = 0.0f;
                            z3 = false;
                        } else {
                            f = f25;
                        }
                        f20 = f18 + convertDpToPixel5 + f20;
                        f21 = f + ((int) paint2.measureText(str5));
                    } else {
                        float f26 = f25 + convertDpToPixel8;
                        if (i10 < length - 1) {
                            f26 += convertDpToPixel2;
                        }
                        f21 = f26;
                        z3 = true;
                    }
                    f19 = Math.max(f19, f21);
                    i10++;
                    convertDpToPixel = f22;
                    legendForm = legendForm2;
                }
                legend.mNeededWidth = f19;
                legend.mNeededHeight = f20;
            }
            legend.mNeededHeight += legend.mYOffset;
            legend.mNeededWidth += legend.mXOffset;
        }
        calculateOffsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:226:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0631  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r40) {
        /*
            Method dump skipped, instructions count: 2392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.github.mikephil.charting.charts.BarLineChartBase.onDraw(android.graphics.Canvas):void");
    }

    public final void onDraw$com$github$mikephil$charting$charts$Chart(Canvas canvas) {
        if (this.mData != null) {
            if (this.mOffsetsCalculated) {
                return;
            }
            calculateOffsets();
            this.mOffsetsCalculated = true;
            return;
        }
        if (!TextUtils.isEmpty(this.mNoDataText)) {
            MPPointF mPPointF = MPPointF.getInstance(getWidth() / 2.0f, getHeight() / 2.0f);
            int i =
                    Chart$2.$SwitchMap$android$graphics$Paint$Align[
                            this.mInfoPaint.getTextAlign().ordinal()];
            if (i == 1) {
                mPPointF.x = 0.0f;
                canvas.drawText(this.mNoDataText, 0.0f, mPPointF.y, this.mInfoPaint);
            } else {
                if (i != 2) {
                    canvas.drawText(this.mNoDataText, mPPointF.x, mPPointF.y, this.mInfoPaint);
                    return;
                }
                float f = (float) (mPPointF.x * 2.0d);
                mPPointF.x = f;
                canvas.drawText(this.mNoDataText, f, mPPointF.y, this.mInfoPaint);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            getChildAt(i5).layout(i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int convertDpToPixel = (int) Utils.convertDpToPixel(50.0f);
        setMeasuredDimension(
                Math.max(getSuggestedMinimumWidth(), ViewGroup.resolveSize(convertDpToPixel, i)),
                Math.max(getSuggestedMinimumHeight(), ViewGroup.resolveSize(convertDpToPixel, i2)));
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        float[] fArr = this.mOnSizeChangedBuffer;
        fArr[1] = 0.0f;
        fArr[0] = 0.0f;
        onSizeChanged$com$github$mikephil$charting$charts$Chart(i, i2, i3, i4);
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        viewPortHandler.refresh(viewPortHandler.mMatrixTouch, this, true);
    }

    public final void onSizeChanged$com$github$mikephil$charting$charts$Chart(
            int i, int i2, int i3, int i4) {
        if (i > 0 && i2 > 0 && i < 10000 && i2 < 10000) {
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            float f = i;
            float f2 = i2;
            RectF rectF = viewPortHandler.mContentRect;
            float f3 = rectF.left;
            float f4 = rectF.top;
            float f5 = viewPortHandler.mChartWidth - rectF.right;
            float f6 = viewPortHandler.mChartHeight - rectF.bottom;
            viewPortHandler.mChartHeight = f2;
            viewPortHandler.mChartWidth = f;
            rectF.set(f3, f4, f - f5, f2 - f6);
        }
        notifyDataSetChanged();
        Iterator it = this.mJobs.iterator();
        while (it.hasNext()) {
            post((Runnable) it.next());
        }
        this.mJobs.clear();
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        BarLineChartTouchListener barLineChartTouchListener = this.mChartTouchListener;
        if (barLineChartTouchListener == null || this.mData == null || !this.mTouchEnabled) {
            return false;
        }
        return barLineChartTouchListener.onTouch(this, motionEvent);
    }

    public final void setData(BarLineScatterCandleBubbleData barLineScatterCandleBubbleData) {
        this.mData = barLineScatterCandleBubbleData;
        this.mOffsetsCalculated = false;
        if (barLineScatterCandleBubbleData == null) {
            return;
        }
        float f = barLineScatterCandleBubbleData.mYMin;
        float f2 = barLineScatterCandleBubbleData.mYMax;
        float roundToNextSignificant =
                Utils.roundToNextSignificant(
                        barLineScatterCandleBubbleData.getEntryCount() < 2
                                ? Math.max(Math.abs(f), Math.abs(f2))
                                : Math.abs(f2 - f));
        this.mDefaultValueFormatter.setup(
                Float.isInfinite(roundToNextSignificant)
                        ? 0
                        : ((int) Math.ceil(-Math.log10(roundToNextSignificant))) + 2);
        for (BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet :
                this.mData.mDataSets) {
            if (barLineScatterCandleBubbleDataSet.mValueFormatter == null
                    || barLineScatterCandleBubbleDataSet.getValueFormatter()
                            == this.mDefaultValueFormatter) {
                DefaultValueFormatter defaultValueFormatter = this.mDefaultValueFormatter;
                if (defaultValueFormatter != null) {
                    barLineScatterCandleBubbleDataSet.mValueFormatter = defaultValueFormatter;
                }
            }
        }
        notifyDataSetChanged();
    }

    public final void setExtraOffsets(float f, float f2) {
        this.mExtraLeftOffset = Utils.convertDpToPixel(0.0f);
        this.mExtraTopOffset = Utils.convertDpToPixel(f);
        this.mExtraRightOffset = Utils.convertDpToPixel(0.0f);
        this.mExtraBottomOffset = Utils.convertDpToPixel(f2);
    }

    public final void setVisibleXRangeMaximum(float f) {
        float f2 = this.mXAxis.mAxisRange / f;
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        viewPortHandler.getClass();
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        viewPortHandler.mMinScaleX = f2;
        viewPortHandler.limitTransAndScale(
                viewPortHandler.mMatrixTouch, viewPortHandler.mContentRect);
    }

    public final void setVisibleXRangeMinimum(float f) {
        float f2 = this.mXAxis.mAxisRange / f;
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        viewPortHandler.getClass();
        if (f2 == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        viewPortHandler.mMaxScaleX = f2;
        viewPortHandler.limitTransAndScale(
                viewPortHandler.mMatrixTouch, viewPortHandler.mContentRect);
    }

    public final void setVisibleYRange(float f, float f2, YAxis.AxisDependency axisDependency) {
        YAxis.AxisDependency axisDependency2 = YAxis.AxisDependency.LEFT;
        float f3 =
                (axisDependency == axisDependency2
                                ? this.mAxisLeft.mAxisRange
                                : this.mAxisRight.mAxisRange)
                        / f;
        float f4 =
                (axisDependency == axisDependency2
                                ? this.mAxisLeft.mAxisRange
                                : this.mAxisRight.mAxisRange)
                        / f2;
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        viewPortHandler.getClass();
        if (f3 < 1.0f) {
            f3 = 1.0f;
        }
        if (f4 == 0.0f) {
            f4 = Float.MAX_VALUE;
        }
        viewPortHandler.mMinScaleY = f3;
        viewPortHandler.mMaxScaleY = f4;
        viewPortHandler.limitTransAndScale(
                viewPortHandler.mMatrixTouch, viewPortHandler.mContentRect);
    }

    public final void highlightValue(Highlight highlight, boolean z) {
        Entry entryForHighlight;
        OnChartValueSelectedListener onChartValueSelectedListener;
        Highlight highlight2;
        if (highlight == null) {
            this.mIndicesToHighlight = null;
            entryForHighlight = null;
        } else {
            entryForHighlight = this.mData.getEntryForHighlight(highlight);
            if (entryForHighlight == null) {
                this.mIndicesToHighlight = null;
                highlight = null;
            } else {
                this.mIndicesToHighlight = new Highlight[] {highlight};
            }
        }
        Highlight[] highlightArr = this.mIndicesToHighlight;
        if (highlightArr == null
                || highlightArr.length <= 0
                || (highlight2 = highlightArr[0]) == null) {
            this.mChartTouchListener.mLastHighlighted = null;
        } else {
            this.mChartTouchListener.mLastHighlighted = highlight2;
        }
        if (z && (onChartValueSelectedListener = this.mSelectionListener) != null) {
            if (highlightArr == null || highlightArr.length <= 0 || highlightArr[0] == null) {
                onChartValueSelectedListener.onNothingSelected();
            } else {
                onChartValueSelectedListener.onValueSelected(entryForHighlight, highlight);
            }
        }
        invalidate();
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mData = null;
        this.mHighLightPerTapEnabled = true;
        this.mDragDecelerationEnabled = true;
        this.mDragDecelerationFrictionCoef = 0.9f;
        this.mDefaultValueFormatter = new DefaultValueFormatter(0);
        this.mTouchEnabled = true;
        this.mNoDataText = "No chart data available.";
        this.mViewPortHandler = new ViewPortHandler();
        this.mExtraTopOffset = 0.0f;
        this.mExtraRightOffset = 0.0f;
        this.mExtraBottomOffset = 0.0f;
        this.mExtraLeftOffset = 0.0f;
        this.mOffsetsCalculated = false;
        this.mMaxHighlightDistance = 0.0f;
        this.mDrawMarkers = true;
        this.mJobs = new ArrayList();
        init();
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mClipDataToContent = true;
        this.mMinOffset = 15.0f;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mData = null;
        this.mHighLightPerTapEnabled = true;
        this.mDragDecelerationEnabled = true;
        this.mDragDecelerationFrictionCoef = 0.9f;
        this.mDefaultValueFormatter = new DefaultValueFormatter(0);
        this.mTouchEnabled = true;
        this.mNoDataText = "No chart data available.";
        this.mViewPortHandler = new ViewPortHandler();
        this.mExtraTopOffset = 0.0f;
        this.mExtraRightOffset = 0.0f;
        this.mExtraBottomOffset = 0.0f;
        this.mExtraLeftOffset = 0.0f;
        this.mOffsetsCalculated = false;
        this.mMaxHighlightDistance = 0.0f;
        this.mDrawMarkers = true;
        this.mJobs = new ArrayList();
        init();
        this.mMaxVisibleCount = 100;
        this.mAutoScaleMinMaxEnabled = false;
        this.mDoubleTapToZoomEnabled = true;
        this.mHighlightPerDragEnabled = true;
        this.mDragXEnabled = true;
        this.mDragYEnabled = true;
        this.mScaleXEnabled = true;
        this.mScaleYEnabled = true;
        this.mDrawGridBackground = false;
        this.mClipDataToContent = true;
        this.mMinOffset = 15.0f;
        this.mOffsetsBuffer = new RectF();
        this.mZoomMatrixBuffer = new Matrix();
        this.mFitScreenMatrixBuffer = new Matrix();
        this.posForGetLowestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.posForGetHighestVisibleX = MPPointD.getInstance(0.0d, 0.0d);
        this.mOnSizeChangedBuffer = new float[2];
    }
}
