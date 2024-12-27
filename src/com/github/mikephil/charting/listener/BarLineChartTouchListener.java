package com.github.mikephil.charting.listener;

import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.settings.wifi.mobileap.clients.report.barchart.WifiApClientsWeeklyBarChart;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarLineChartTouchListener extends GestureDetector.SimpleOnGestureListener
        implements View.OnTouchListener {
    public BarLineChartBase mChart;
    public BarLineScatterCandleBubbleDataSet mClosestDataSetToTouch;
    public MPPointF mDecelerationCurrentPoint;
    public long mDecelerationLastTime;
    public MPPointF mDecelerationVelocity;
    public float mDragTriggerDist;
    public GestureDetector mGestureDetector;
    public ChartTouchListener$ChartGesture mLastGesture;
    public Highlight mLastHighlighted;
    public Matrix mMatrix;
    public float mMinScalePointerDistance;
    public float mSavedDist;
    public Matrix mSavedMatrix;
    public float mSavedXDist;
    public float mSavedYDist;
    public int mTouchMode;
    public MPPointF mTouchPointCenter;
    public MPPointF mTouchStartPoint;
    public VelocityTracker mVelocityTracker;

    public static float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((y * y) + (x * x));
    }

    public final void endAction(MotionEvent motionEvent) {
        if (this.mChart.mGestureListener != null) {
            ChartTouchListener$ChartGesture chartTouchListener$ChartGesture = this.mLastGesture;
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d(
                    "WifiApClientsWeeklyBarChart",
                    "onChartGestureEnd  " + motionEvent + ": " + chartTouchListener$ChartGesture);
        }
    }

    public final MPPointF getTrans(float f, float f2) {
        ViewPortHandler viewPortHandler = this.mChart.mViewPortHandler;
        float f3 = f - viewPortHandler.mContentRect.left;
        inverted();
        return MPPointF.getInstance(
                f3,
                -((this.mChart.getMeasuredHeight() - f2)
                        - (viewPortHandler.mChartHeight - viewPortHandler.mContentRect.bottom)));
    }

    public final void inverted() {
        if (this.mClosestDataSetToTouch == null) {
            BarLineChartBase barLineChartBase = this.mChart;
            barLineChartBase.mAxisLeft.getClass();
            barLineChartBase.mAxisRight.getClass();
        }
        BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet =
                this.mClosestDataSetToTouch;
        if (barLineScatterCandleBubbleDataSet != null) {
            this.mChart.isInverted(barLineScatterCandleBubbleDataSet.mAxisDependency);
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnDoubleTapListener
    public final boolean onDoubleTap(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener$ChartGesture.DOUBLE_TAP;
        WifiApClientsWeeklyBarChart.AnonymousClass1 anonymousClass1 = this.mChart.mGestureListener;
        if (anonymousClass1 != null) {
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "onChartDoubleTapped  ");
        }
        BarLineChartBase barLineChartBase = this.mChart;
        if (barLineChartBase.mDoubleTapToZoomEnabled
                && barLineChartBase.mData.getEntryCount() > 0) {
            MPPointF trans = getTrans(motionEvent.getX(), motionEvent.getY());
            BarLineChartBase barLineChartBase2 = this.mChart;
            float f = barLineChartBase2.mScaleXEnabled ? 1.4f : 1.0f;
            float f2 = barLineChartBase2.mScaleYEnabled ? 1.4f : 1.0f;
            float f3 = trans.x;
            float f4 = trans.y;
            ViewPortHandler viewPortHandler = barLineChartBase2.mViewPortHandler;
            Matrix matrix = barLineChartBase2.mZoomMatrixBuffer;
            viewPortHandler.getClass();
            matrix.reset();
            matrix.set(viewPortHandler.mMatrixTouch);
            matrix.postScale(f, f2, f3, -f4);
            barLineChartBase2.mViewPortHandler.refresh(
                    barLineChartBase2.mZoomMatrixBuffer, barLineChartBase2, false);
            barLineChartBase2.calculateOffsets();
            barLineChartBase2.postInvalidate();
            this.mChart.getClass();
            if (anonymousClass1 != null) {
                int[] iArr2 = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                Log.d("WifiApClientsWeeklyBarChart", "onChartScale  ");
            }
            MPPointF.pool.recycle(trans);
        }
        return super.onDoubleTap(motionEvent);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public final boolean onFling(
            MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mLastGesture = ChartTouchListener$ChartGesture.FLING;
        if (this.mChart.mGestureListener != null) {
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "onChartFling  ");
        }
        return super.onFling(motionEvent, motionEvent2, f, f2);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public final void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener$ChartGesture.LONG_PRESS;
        if (this.mChart.mGestureListener != null) {
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "onChartLongPressed  ");
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener$ChartGesture.SINGLE_TAP;
        WifiApClientsWeeklyBarChart.AnonymousClass1 anonymousClass1 = this.mChart.mGestureListener;
        if (anonymousClass1 != null) {
            WifiApClientsWeeklyBarChart.this.mSingleTapEnable = true;
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "onChartSingleTapped  ");
        }
        BarLineChartBase barLineChartBase = this.mChart;
        if (!barLineChartBase.mHighLightPerTapEnabled) {
            return false;
        }
        Highlight highlightByTouchPoint =
                barLineChartBase.getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY());
        if (highlightByTouchPoint == null || highlightByTouchPoint.equalTo(this.mLastHighlighted)) {
            this.mChart.highlightValue(null, true);
            this.mLastHighlighted = null;
        } else {
            this.mChart.highlightValue(highlightByTouchPoint, true);
            this.mLastHighlighted = highlightByTouchPoint;
        }
        return super.onSingleTapUp(motionEvent);
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Highlight highlightByTouchPoint;
        VelocityTracker velocityTracker;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (motionEvent.getActionMasked() == 3
                && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mTouchMode == 0) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        BarLineChartBase barLineChartBase = this.mChart;
        int i = 0;
        if (!(barLineChartBase.mDragXEnabled || barLineChartBase.mDragYEnabled)
                && !barLineChartBase.mScaleXEnabled
                && !barLineChartBase.mScaleYEnabled) {
            return true;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            WifiApClientsWeeklyBarChart.AnonymousClass1 anonymousClass1 =
                    this.mChart.mGestureListener;
            if (anonymousClass1 != null) {
                int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                Log.d("WifiApClientsWeeklyBarChart", "onChartGestureStart  ");
                motionEvent.getX();
                WifiApClientsWeeklyBarChart.this.getClass();
            }
            MPPointF mPPointF = this.mDecelerationVelocity;
            mPPointF.x = 0.0f;
            mPPointF.y = 0.0f;
            saveTouchStart(motionEvent);
        } else if (action == 1) {
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            int pointerId = motionEvent.getPointerId(0);
            velocityTracker2.computeCurrentVelocity(1000, Utils.mMaximumFlingVelocity);
            float yVelocity = velocityTracker2.getYVelocity(pointerId);
            float xVelocity = velocityTracker2.getXVelocity(pointerId);
            if ((Math.abs(xVelocity) > Utils.mMinimumFlingVelocity
                            || Math.abs(yVelocity) > Utils.mMinimumFlingVelocity)
                    && this.mTouchMode == 1
                    && this.mChart.mDragDecelerationEnabled) {
                MPPointF mPPointF2 = this.mDecelerationVelocity;
                mPPointF2.x = 0.0f;
                mPPointF2.y = 0.0f;
                this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDecelerationCurrentPoint.x = motionEvent.getX();
                this.mDecelerationCurrentPoint.y = motionEvent.getY();
                MPPointF mPPointF3 = this.mDecelerationVelocity;
                mPPointF3.x = xVelocity;
                mPPointF3.y = yVelocity;
                this.mChart.postInvalidateOnAnimation();
            }
            int i2 = this.mTouchMode;
            if (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5) {
                this.mChart.calculateOffsets();
                this.mChart.postInvalidate();
            }
            this.mTouchMode = 0;
            ViewParent parent = this.mChart.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(false);
            }
            VelocityTracker velocityTracker3 = this.mVelocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
                this.mVelocityTracker = null;
            }
            endAction(motionEvent);
        } else if (action == 2) {
            int i3 = this.mTouchMode;
            if (i3 == 1) {
                ViewParent parent2 = this.mChart.getParent();
                if (parent2 != null) {
                    parent2.requestDisallowInterceptTouchEvent(true);
                }
                performDrag(
                        this.mChart.mDragXEnabled
                                ? motionEvent.getX() - this.mTouchStartPoint.x
                                : 0.0f,
                        this.mChart.mDragYEnabled
                                ? motionEvent.getY() - this.mTouchStartPoint.y
                                : 0.0f);
            } else {
                if (i3 == 2 || i3 == 3 || i3 == 4) {
                    ViewParent parent3 = this.mChart.getParent();
                    if (parent3 != null) {
                        parent3.requestDisallowInterceptTouchEvent(true);
                    }
                    BarLineChartBase barLineChartBase2 = this.mChart;
                    if ((barLineChartBase2.mScaleXEnabled || barLineChartBase2.mScaleYEnabled)
                            && motionEvent.getPointerCount() >= 2) {
                        WifiApClientsWeeklyBarChart.AnonymousClass1 anonymousClass12 =
                                this.mChart.mGestureListener;
                        float spacing = spacing(motionEvent);
                        if (spacing > this.mMinScalePointerDistance) {
                            MPPointF mPPointF4 = this.mTouchPointCenter;
                            MPPointF trans = getTrans(mPPointF4.x, mPPointF4.y);
                            BarLineChartBase barLineChartBase3 = this.mChart;
                            ViewPortHandler viewPortHandler = barLineChartBase3.mViewPortHandler;
                            int i4 = this.mTouchMode;
                            if (i4 == 4) {
                                this.mLastGesture = ChartTouchListener$ChartGesture.PINCH_ZOOM;
                                float f = spacing / this.mSavedDist;
                                boolean z = f < 1.0f;
                                boolean z2 =
                                        !z
                                                ? viewPortHandler.mScaleX
                                                        >= viewPortHandler.mMaxScaleX
                                                : viewPortHandler.mScaleX
                                                        <= viewPortHandler.mMinScaleX;
                                if (!z
                                        ? viewPortHandler.mScaleY < viewPortHandler.mMaxScaleY
                                        : viewPortHandler.mScaleY > viewPortHandler.mMinScaleY) {
                                    i = 1;
                                }
                                float f2 = barLineChartBase3.mScaleXEnabled ? f : 1.0f;
                                float f3 = barLineChartBase3.mScaleYEnabled ? f : 1.0f;
                                if (i != 0 || z2) {
                                    this.mMatrix.set(this.mSavedMatrix);
                                    this.mMatrix.postScale(f2, f3, trans.x, trans.y);
                                    if (anonymousClass12 != null) {
                                        int[] iArr2 =
                                                WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                                        Log.d("WifiApClientsWeeklyBarChart", "onChartScale  ");
                                    }
                                }
                            } else if (i4 == 2 && barLineChartBase3.mScaleXEnabled) {
                                this.mLastGesture = ChartTouchListener$ChartGesture.X_ZOOM;
                                float abs =
                                        Math.abs(motionEvent.getX(0) - motionEvent.getX(1))
                                                / this.mSavedXDist;
                                if (abs >= 1.0f
                                        ? viewPortHandler.mScaleX < viewPortHandler.mMaxScaleX
                                        : viewPortHandler.mScaleX > viewPortHandler.mMinScaleX) {
                                    this.mMatrix.set(this.mSavedMatrix);
                                    this.mMatrix.postScale(abs, 1.0f, trans.x, trans.y);
                                    if (anonymousClass12 != null) {
                                        int[] iArr3 =
                                                WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                                        Log.d("WifiApClientsWeeklyBarChart", "onChartScale  ");
                                    }
                                }
                            } else if (i4 == 3 && barLineChartBase3.mScaleYEnabled) {
                                this.mLastGesture = ChartTouchListener$ChartGesture.Y_ZOOM;
                                float abs2 =
                                        Math.abs(motionEvent.getY(0) - motionEvent.getY(1))
                                                / this.mSavedYDist;
                                if (abs2 >= 1.0f
                                        ? viewPortHandler.mScaleY < viewPortHandler.mMaxScaleY
                                        : viewPortHandler.mScaleY > viewPortHandler.mMinScaleY) {
                                    this.mMatrix.set(this.mSavedMatrix);
                                    this.mMatrix.postScale(1.0f, abs2, trans.x, trans.y);
                                    if (anonymousClass12 != null) {
                                        int[] iArr4 =
                                                WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
                                        Log.d("WifiApClientsWeeklyBarChart", "onChartScale  ");
                                    }
                                }
                            }
                            MPPointF.pool.recycle(trans);
                        }
                    }
                } else if (i3 == 0) {
                    float x = motionEvent.getX() - this.mTouchStartPoint.x;
                    float y = motionEvent.getY() - this.mTouchStartPoint.y;
                    if (Math.abs((float) Math.sqrt((y * y) + (x * x))) > this.mDragTriggerDist) {
                        BarLineChartBase barLineChartBase4 = this.mChart;
                        if (barLineChartBase4.mDragXEnabled || barLineChartBase4.mDragYEnabled) {
                            ViewPortHandler viewPortHandler2 = barLineChartBase4.mViewPortHandler;
                            float f4 = viewPortHandler2.mScaleX;
                            float f5 = viewPortHandler2.mMinScaleX;
                            if (f4 <= f5 && f5 <= 1.0f) {
                                i = 1;
                            }
                            ChartTouchListener$ChartGesture chartTouchListener$ChartGesture =
                                    ChartTouchListener$ChartGesture.DRAG;
                            if (i != 0) {
                                float f6 = viewPortHandler2.mScaleY;
                                float f7 = viewPortHandler2.mMinScaleY;
                                if (f6 <= f7 && f7 <= 1.0f) {
                                    viewPortHandler2.getClass();
                                    BarLineChartBase barLineChartBase5 = this.mChart;
                                    boolean z3 = barLineChartBase5.mHighlightPerDragEnabled;
                                    if (z3) {
                                        this.mLastGesture = chartTouchListener$ChartGesture;
                                        if (z3
                                                && (highlightByTouchPoint =
                                                                barLineChartBase5
                                                                        .getHighlightByTouchPoint(
                                                                                motionEvent.getX(),
                                                                                motionEvent.getY()))
                                                        != null
                                                && !highlightByTouchPoint.equalTo(
                                                        this.mLastHighlighted)) {
                                            this.mLastHighlighted = highlightByTouchPoint;
                                            this.mChart.highlightValue(highlightByTouchPoint, true);
                                        }
                                    }
                                }
                            }
                            float abs3 = Math.abs(motionEvent.getX() - this.mTouchStartPoint.x);
                            float abs4 = Math.abs(motionEvent.getY() - this.mTouchStartPoint.y);
                            BarLineChartBase barLineChartBase6 = this.mChart;
                            if ((barLineChartBase6.mDragXEnabled || abs4 >= abs3)
                                    && (barLineChartBase6.mDragYEnabled || abs4 <= abs3)) {
                                this.mLastGesture = chartTouchListener$ChartGesture;
                                this.mTouchMode = 1;
                            }
                        }
                    }
                }
            }
        } else if (action == 3) {
            this.mTouchMode = 0;
            endAction(motionEvent);
        } else if (action != 5) {
            if (action == 6) {
                VelocityTracker velocityTracker4 = this.mVelocityTracker;
                velocityTracker4.computeCurrentVelocity(1000, Utils.mMaximumFlingVelocity);
                int actionIndex = motionEvent.getActionIndex();
                int pointerId2 = motionEvent.getPointerId(actionIndex);
                float xVelocity2 = velocityTracker4.getXVelocity(pointerId2);
                float yVelocity2 = velocityTracker4.getYVelocity(pointerId2);
                int pointerCount = motionEvent.getPointerCount();
                while (true) {
                    if (i >= pointerCount) {
                        break;
                    }
                    if (i != actionIndex) {
                        int pointerId3 = motionEvent.getPointerId(i);
                        if ((velocityTracker4.getYVelocity(pointerId3) * yVelocity2)
                                        + (velocityTracker4.getXVelocity(pointerId3) * xVelocity2)
                                < 0.0f) {
                            velocityTracker4.clear();
                            break;
                        }
                    }
                    i++;
                }
                this.mTouchMode = 5;
            }
        } else if (motionEvent.getPointerCount() >= 2) {
            ViewParent parent4 = this.mChart.getParent();
            if (parent4 != null) {
                parent4.requestDisallowInterceptTouchEvent(true);
            }
            saveTouchStart(motionEvent);
            this.mSavedXDist = Math.abs(motionEvent.getX(0) - motionEvent.getX(1));
            this.mSavedYDist = Math.abs(motionEvent.getY(0) - motionEvent.getY(1));
            float spacing2 = spacing(motionEvent);
            this.mSavedDist = spacing2;
            if (spacing2 > 10.0f) {
                this.mChart.getClass();
                BarLineChartBase barLineChartBase7 = this.mChart;
                boolean z4 = barLineChartBase7.mScaleXEnabled;
                if (z4 != barLineChartBase7.mScaleYEnabled) {
                    this.mTouchMode = z4 ? 2 : 3;
                } else {
                    this.mTouchMode = this.mSavedXDist > this.mSavedYDist ? 2 : 3;
                }
            }
            MPPointF mPPointF5 = this.mTouchPointCenter;
            float x2 = motionEvent.getX(1) + motionEvent.getX(0);
            float y2 = motionEvent.getY(1) + motionEvent.getY(0);
            mPPointF5.x = x2 / 2.0f;
            mPPointF5.y = y2 / 2.0f;
        }
        BarLineChartBase barLineChartBase8 = this.mChart;
        ViewPortHandler viewPortHandler3 = barLineChartBase8.mViewPortHandler;
        Matrix matrix = this.mMatrix;
        viewPortHandler3.refresh(matrix, barLineChartBase8, true);
        this.mMatrix = matrix;
        return true;
    }

    public final void performDrag(float f, float f2) {
        this.mLastGesture = ChartTouchListener$ChartGesture.DRAG;
        this.mMatrix.set(this.mSavedMatrix);
        WifiApClientsWeeklyBarChart.AnonymousClass1 anonymousClass1 = this.mChart.mGestureListener;
        inverted();
        this.mMatrix.postTranslate(f, f2);
        if (anonymousClass1 != null) {
            int[] iArr = WifiApClientsWeeklyBarChart.STACKED_BAR_COLORS;
            Log.d("WifiApClientsWeeklyBarChart", "onChartTranslate  ");
        }
    }

    public final void saveTouchStart(MotionEvent motionEvent) {
        this.mSavedMatrix.set(this.mMatrix);
        this.mTouchStartPoint.x = motionEvent.getX();
        this.mTouchStartPoint.y = motionEvent.getY();
        BarLineChartBase barLineChartBase = this.mChart;
        Highlight highlightByTouchPoint =
                barLineChartBase.getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY());
        this.mClosestDataSetToTouch =
                highlightByTouchPoint != null
                        ? barLineChartBase.mData.getDataSetByIndex(
                                highlightByTouchPoint.mDataSetIndex)
                        : null;
    }
}
