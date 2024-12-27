package com.samsung.android.settings.wifi.develop.history.view;

import android.R;
import android.annotation.Nullable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HistoryGraph extends View {
    public int mActivePointerId;
    public final int mBottomPadding;
    public HistoryView.AnonymousClass1 mChartListener;
    public final DisplayMetrics mDisplayMetrics;
    public final Drawable mDivider;
    public final int mDividerSize;
    public final Paint mDottedLinePaint;
    public final Paint mLabelPaint;
    public float mLastTouchX;
    public float mLastTouchY;
    public final Paint mLinePaint;
    public final List mLocalPaths;
    public final List mLocalSecondPaths;
    public float mMaxX;
    public float mMaxY;
    public final float mMiddleDividerLoc;
    public final Path mPath;
    public int mPathLabelAlign;
    public final List mPaths;
    public float mPosX;
    public final ScaleGestureDetector mScaleDetector;
    public float mScaleFactor;
    public float mScatterDensity;
    public final Paint mScatterPaint;
    public float mScatterRadius;
    public final Paint mSecondLinePaint;
    public int mSecondPathLabelAlign;
    public final List mSecondPaths;
    public boolean mShowValueLabel;
    public final int mTopPadding;
    public long mValueAxisOffset;
    public long mValueAxisTick;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public ScaleListener() {}

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener,
                  // android.view.ScaleGestureDetector.OnScaleGestureListener
        public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            HistoryGraph historyGraph = HistoryGraph.this;
            historyGraph.mScaleFactor =
                    Math.max(1.0f, Math.min(historyGraph.mScaleFactor * scaleFactor, 100000.0f));
            float f = HistoryGraph.this.mScaleFactor;
            if (f <= 1.0f || f >= 100000.0f) {
                return false;
            }
            float focusX = scaleGestureDetector.getFocusX();
            HistoryGraph historyGraph2 = HistoryGraph.this;
            float f2 = historyGraph2.mPosX;
            float f3 = focusX - f2;
            historyGraph2.mPosX = f2 - ((scaleFactor * f3) - f3);
            historyGraph2.invalidate();
            return true;
        }
    }

    public HistoryGraph(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScatterDensity = 0.0f;
        this.mPath = new Path();
        this.mPathLabelAlign = 0;
        this.mPaths = new ArrayList();
        this.mLocalPaths = new ArrayList();
        this.mSecondPaths = new ArrayList();
        this.mLocalSecondPaths = new ArrayList();
        this.mMaxX = 100.0f;
        this.mMaxY = 100.0f;
        this.mMiddleDividerLoc = 0.5f;
        this.mScaleFactor = 1.0f;
        this.mActivePointerId = -1;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        float f = displayMetrics.scaledDensity;
        float f2 = f * 2.0f;
        this.mScatterRadius = f2;
        this.mDividerSize = (int) (1.0f * f);
        int i = (int) f2;
        this.mTopPadding = i;
        this.mBottomPadding = i;
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        paint.setAntiAlias(false);
        paint.setColor(Color.rgb(180, 180, 180));
        Paint paint2 = new Paint(paint);
        this.mSecondLinePaint = paint2;
        paint2.setColor(Color.rgb(63, 145, 255));
        Paint paint3 = new Paint(paint);
        this.mDottedLinePaint = paint3;
        float f3 = displayMetrics.scaledDensity;
        paint3.setStrokeWidth(3.0f);
        paint3.setPathEffect(new DashPathEffect(new float[] {2.0f * f3, f3 * 4.0f}, 0.0f));
        Paint paint4 = new Paint(paint);
        this.mScatterPaint = paint4;
        paint4.setColor(Color.rgb(62, 133, 160));
        Paint paint5 = new Paint();
        this.mLabelPaint = paint5;
        paint5.setColor(Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, 131, 67));
        paint5.setAntiAlias(true);
        paint5.setTextAlign(Paint.Align.CENTER);
        paint5.setTextSize(f * 10.0f);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.listDivider, typedValue, true);
        this.mDivider = context.getDrawable(typedValue.resourceId);
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public final void calculateLocalPaths(List list, List list2) {
        if (getWidth() == 0) {
            return;
        }
        list2.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            float longValue = ((((Long) pair.first).longValue() / this.mMaxX) * getWidth()) + 0;
            float longValue2 = ((Long) pair.second).longValue();
            int height = getHeight() - this.mTopPadding;
            int i = this.mBottomPadding;
            list2.add(
                    new PointF(
                            longValue,
                            ((int) ((1.0f - (longValue2 / this.mMaxY)) * (height - i))) + i));
        }
    }

    public final void drawDivider(int i, Canvas canvas) {
        Drawable drawable = this.mDivider;
        drawable.setBounds(0, i, getWidth(), this.mDividerSize + i);
        drawable.draw(canvas);
    }

    public final void drawLineAndScatterAndLabel(
            Canvas canvas, long j, List list, List list2, Paint paint, int i) {
        ArrayList arrayList;
        int i2;
        int i3 = i;
        ArrayList arrayList2 = (ArrayList) list2;
        if (arrayList2.isEmpty()) {
            return;
        }
        float width = getWidth();
        float f = this.mScatterRadius;
        long j2 = (long) ((width / (f * 2.0f)) * this.mScatterDensity);
        if (j >= j2) {
            f = ((float) j) < ((float) j2) * 2.0f ? f / 2.0f : 0.0f;
        }
        int width2 = getWidth();
        Path path = new Path();
        Path path2 = new Path();
        this.mPath.reset();
        float f2 = Float.NaN;
        float f3 = Float.NaN;
        int i4 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (i4 >= arrayList2.size()) {
                break;
            }
            float f4 = ((PointF) arrayList2.get(i4)).x * this.mScaleFactor;
            float f5 = ((PointF) arrayList2.get(i4)).y;
            if (f4 < Math.abs(this.mPosX)) {
                i2 = width2;
                arrayList = arrayList2;
            } else {
                arrayList = arrayList2;
                float f6 = width2;
                if (f4 > Math.abs(this.mPosX) + f6) {
                    float abs = Math.abs(this.mPosX) + f6;
                    float f7 = (((abs - f3) * (f5 - f2)) / (f4 - f3)) + f2;
                    if (!Float.isNaN(f3) && !Float.isNaN(f2)) {
                        path.moveTo(f3, f2);
                        path.lineTo(abs, f7);
                    }
                } else {
                    boolean z3 = true;
                    if (!z) {
                        if (!Float.isNaN(f3) && !Float.isNaN(f2)) {
                            path2.moveTo(f3, f2);
                            path2.lineTo(f4, f5);
                        }
                        z = true;
                    }
                    if (z2) {
                        this.mPath.lineTo(f4, f5);
                    } else {
                        this.mPath.moveTo(f4, f5);
                        z2 = true;
                    }
                    if (f > 0.0f) {
                        canvas.drawCircle(f4, f5, f, this.mScatterPaint);
                    }
                    if (!this.mShowValueLabel || j >= j2) {
                        i2 = width2;
                    } else {
                        String valueOf =
                                String.valueOf(
                                        ((Long) ((Pair) ((ArrayList) list).get(i4)).second)
                                                        .longValue()
                                                + this.mValueAxisOffset);
                        if (i3 != 0 ? i3 != 1 : i4 % 2 != 1) {
                            z3 = false;
                        }
                        i2 = width2;
                        this.mLabelPaint.getTextBounds(valueOf, 0, valueOf.length(), new Rect());
                        canvas.drawText(
                                valueOf,
                                (r11.width() / 2.0f) + f4,
                                z3
                                        ? f5 - (r11.height() / 2.0f)
                                        : r11.height() + f5 + (this.mScatterRadius * 2.0f),
                                this.mLabelPaint);
                        i4++;
                        width2 = i2;
                        i3 = i;
                        f3 = f4;
                        f2 = f5;
                        arrayList2 = arrayList;
                    }
                }
            }
            i4++;
            width2 = i2;
            i3 = i;
            f3 = f4;
            f2 = f5;
            arrayList2 = arrayList;
        }
        canvas.drawPath(this.mPath, paint);
        if (!path2.isEmpty()) {
            canvas.drawPath(path2, this.mDottedLinePaint);
        }
        if (path.isEmpty()) {
            return;
        }
        canvas.drawPath(path, this.mDottedLinePaint);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        HistoryView historyView;
        Object[] objArr;
        int width = getWidth();
        int height = (getHeight() - this.mTopPadding) - this.mBottomPadding;
        int i = 0;
        drawDivider(0, canvas);
        drawDivider((int) ((height - this.mDividerSize) * this.mMiddleDividerLoc), canvas);
        drawDivider(height - this.mDividerSize, canvas);
        if (((ArrayList) this.mLocalPaths).isEmpty()
                && ((ArrayList) this.mLocalSecondPaths).isEmpty()) {
            return;
        }
        canvas.save();
        if (getLayoutDirection() == 1) {
            canvas.scale(-1.0f, 1.0f, width * 0.5f, 0.0f);
        }
        float f = this.mPosX;
        if (f < 0.0f) {
            float f2 = width;
            this.mPosX = Math.max(f, -((this.mScaleFactor * f2) - f2));
        } else {
            this.mPosX = Math.min(f, 0.0f);
        }
        canvas.translate(this.mPosX, 0.0f);
        float f3 = width;
        float f4 = this.mScaleFactor * f3;
        float abs = Math.abs(this.mPosX) / f4;
        float abs2 = (Math.abs(this.mPosX) + f3) / f4;
        long j = ((long) ((abs2 - abs) * this.mMaxX)) / this.mValueAxisTick;
        if (((ArrayList) this.mSecondPaths).isEmpty()) {
            this.mPathLabelAlign = 0;
        } else {
            this.mPathLabelAlign = 1;
            this.mSecondPathLabelAlign = 2;
        }
        drawLineAndScatterAndLabel(
                canvas,
                j,
                this.mSecondPaths,
                this.mLocalSecondPaths,
                this.mSecondLinePaint,
                this.mSecondPathLabelAlign);
        drawLineAndScatterAndLabel(
                canvas, j, this.mPaths, this.mLocalPaths, this.mLinePaint, this.mPathLabelAlign);
        canvas.restore();
        HistoryView.AnonymousClass1 anonymousClass1 = this.mChartListener;
        if (anonymousClass1 == null
                || (objArr = (historyView = HistoryView.this).mDomainAxisValues) == null
                || objArr.length == 0) {
            return;
        }
        if (objArr[0] instanceof Date) {
            long j2 = historyView.mMaxDomainValue;
            Format format = historyView.mDomainAxisFormat;
            if (format == null) {
                format = new SimpleDateFormat("MM-dd HH:mm:ss");
            }
            Date date = new Date(((Date) historyView.mDomainAxisValues[0]).getTime());
            float f5 = j2;
            date.setTime(date.getTime() + ((long) (abs * f5)));
            historyView.mDomainAxisLabels[0].setText(format.format(date));
            if (historyView.mDomainAxisLabels.length == 1) {
                return;
            }
            Object[] objArr2 = historyView.mDomainAxisValues;
            Date date2 = new Date(((Date) objArr2[objArr2.length - 1]).getTime());
            date2.setTime(date2.getTime() - ((long) ((1.0f - abs2) * f5)));
            TextView[] textViewArr = historyView.mDomainAxisLabels;
            textViewArr[textViewArr.length - 1].setText(format.format(date2));
            return;
        }
        while (true) {
            TextView[] textViewArr2 = historyView.mDomainAxisLabels;
            if (i >= textViewArr2.length) {
                return;
            }
            textViewArr2[i].setText(historyView.mDomainAxisValues[i].toString());
            i++;
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        calculateLocalPaths(this.mPaths, this.mLocalPaths);
        calculateLocalPaths(this.mSecondPaths, this.mLocalSecondPaths);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mScaleDetector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    float x = motionEvent.getX(findPointerIndex);
                    motionEvent.getY(findPointerIndex);
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (!this.mScaleDetector.isInProgress()) {
                        this.mPosX += x - this.mLastTouchX;
                        invalidate();
                    }
                    this.mLastTouchX = x;
                } else if (action != 3) {
                    if (action == 6) {
                        int action2 = (motionEvent.getAction() & 65280) >> 8;
                        if (motionEvent.getPointerId(action2) == this.mActivePointerId) {
                            int i = action2 == 0 ? 1 : 0;
                            this.mLastTouchX = motionEvent.getX(i);
                            motionEvent.getY(i);
                            this.mActivePointerId = motionEvent.getPointerId(i);
                        }
                    }
                }
            }
            getParent().requestDisallowInterceptTouchEvent(false);
            this.mActivePointerId = -1;
        } else {
            this.mLastTouchX = motionEvent.getX();
            motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        }
        return true;
    }
}
