package com.samsung.android.settings.wifi.develop.homewifi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RssiChart extends LineChart {
    public static final String[] COLOR_PALLETS = {
        "#ca84e8", "#1bceb5", "#6b8ea6", "#78a2f1", "#ffbb4d", "#987fff", "#4ccbff"
    };
    public final boolean mAutoScrollEnabled;
    public final ArrayList mColors;
    public final float[] mConvertedPts;
    public boolean mInWeakAreaNow;
    public int mNumOfDataSets;
    public Paint mWeakAreaPaint;
    public final ArrayList mWeakAreas;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ColoredYAxisRenderer extends YAxisRenderer {
        public final Paint mBackgroundPaint;
        public final int mDefaultLabelColor;
        public final int mWeakSignalLabelColor;

        public ColoredYAxisRenderer(
                ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer, int i) {
            super(viewPortHandler, yAxis, transformer);
            this.mDefaultLabelColor = i;
            this.mWeakSignalLabelColor = Color.parseColor("#d93e36");
            Paint paint = new Paint();
            this.mBackgroundPaint = paint;
            paint.setColor(Color.parseColor("#a4ffffff"));
        }

        @Override // com.github.mikephil.charting.renderer.YAxisRenderer
        public final void drawYLabels(Canvas canvas, float f, float[] fArr, float f2) {
            YAxis yAxis = this.mYAxis;
            int i = yAxis.mDrawTopYLabelEntry ? yAxis.mEntryCount : yAxis.mEntryCount - 1;
            for (int i2 = !yAxis.mDrawBottomYLabelEntry ? 1 : 0; i2 < i; i2++) {
                String formattedLabel = yAxis.getFormattedLabel(i2);
                this.mAxisLabelPaint.setColor(
                        Integer.toString(-70).equals(formattedLabel)
                                ? this.mWeakSignalLabelColor
                                : this.mDefaultLabelColor);
                canvas.drawText(formattedLabel, f, fArr[(i2 * 2) + 1] + f2, this.mAxisLabelPaint);
            }
        }

        @Override // com.github.mikephil.charting.renderer.YAxisRenderer
        public final void renderLimitLines(Canvas canvas) {
            List list = this.mYAxis.mLimitLines;
            if (list != null) {
                ArrayList arrayList = (ArrayList) list;
                if (arrayList.size() <= 0) {
                    return;
                }
                float[] fArr = this.mRenderLimitLinesBuffer;
                int i = 0;
                float f = 0.0f;
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                Path path = this.mRenderLimitLines;
                path.reset();
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    LimitLine limitLine = (LimitLine) arrayList.get(i2);
                    if (limitLine.mEnabled) {
                        int save = canvas.save();
                        RectF rectF = this.mLimitLineClippingRect;
                        ViewPortHandler viewPortHandler = this.mViewPortHandler;
                        rectF.set(viewPortHandler.mContentRect);
                        this.mLimitLineClippingRect.inset(f, -limitLine.mLineWidth);
                        canvas.clipRect(this.mLimitLineClippingRect);
                        this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                        this.mLimitLinePaint.setColor(limitLine.mLineColor);
                        this.mLimitLinePaint.setStrokeWidth(limitLine.mLineWidth);
                        this.mLimitLinePaint.setPathEffect(limitLine.mDashPathEffect);
                        fArr[1] = limitLine.mLimit;
                        this.mTrans.pointValuesToPixel(fArr);
                        path.moveTo(viewPortHandler.mContentRect.left, fArr[1]);
                        path.lineTo(viewPortHandler.mContentRect.right, fArr[1]);
                        canvas.drawPath(path, this.mLimitLinePaint);
                        path.reset();
                        String str = limitLine.mLabel;
                        if (str != null && !str.equals(ApnSettings.MVNO_NONE)) {
                            this.mLimitLinePaint.setStyle(limitLine.mTextStyle);
                            this.mLimitLinePaint.setPathEffect(null);
                            this.mLimitLinePaint.setColor(limitLine.mTextColor);
                            this.mLimitLinePaint.setTypeface(limitLine.mTypeface);
                            this.mLimitLinePaint.setStrokeWidth(0.5f);
                            this.mLimitLinePaint.setTextSize(limitLine.mTextSize);
                            float calcTextHeight = Utils.calcTextHeight(this.mLimitLinePaint, str);
                            float convertDpToPixel =
                                    Utils.convertDpToPixel(4.0f) + limitLine.mXOffset;
                            float f2 = limitLine.mLineWidth + calcTextHeight + limitLine.mYOffset;
                            LimitLine.LimitLabelPosition limitLabelPosition =
                                    limitLine.mLabelPosition;
                            if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                                this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                                canvas.drawText(
                                        str,
                                        viewPortHandler.mContentRect.right - convertDpToPixel,
                                        (fArr[1] - f2) + calcTextHeight,
                                        this.mLimitLinePaint);
                            } else if (limitLabelPosition
                                    == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                                Rect rect = new Rect();
                                this.mLimitLinePaint.getTextBounds(str, i, str.length(), rect);
                                rect.offsetTo(
                                        (int)
                                                ((viewPortHandler.mContentRect.right
                                                                - convertDpToPixel)
                                                        - rect.width()),
                                        (int) ((fArr[1] + f2) - rect.height()));
                                rect.left = (int) (rect.left - Utils.convertDpToPixel(5.0f));
                                rect.right = (int) (Utils.convertDpToPixel(5.0f) + rect.right);
                                rect.bottom = (int) (Utils.convertDpToPixel(4.0f) + rect.bottom);
                                canvas.drawRoundRect(
                                        new RectF(rect),
                                        Utils.convertDpToPixel(8.0f),
                                        Utils.convertDpToPixel(8.0f),
                                        this.mBackgroundPaint);
                                this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                                canvas.drawText(
                                        str,
                                        viewPortHandler.mContentRect.right - convertDpToPixel,
                                        fArr[1] + f2,
                                        this.mLimitLinePaint);
                            } else if (limitLabelPosition
                                    == LimitLine.LimitLabelPosition.LEFT_TOP) {
                                this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                                canvas.drawText(
                                        str,
                                        viewPortHandler.mContentRect.left + convertDpToPixel,
                                        (fArr[1] - f2) + calcTextHeight,
                                        this.mLimitLinePaint);
                            } else {
                                this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                                canvas.drawText(
                                        str,
                                        viewPortHandler.mContentRect.left + convertDpToPixel,
                                        fArr[1] + f2,
                                        this.mLimitLinePaint);
                            }
                        }
                        canvas.restoreToCount(save);
                    }
                    i2++;
                    i = 0;
                    f = 0.0f;
                }
            }
        }
    }

    public RssiChart(Context context) {
        super(context);
        this.mColors = new ArrayList();
        this.mWeakAreas = new ArrayList();
        this.mConvertedPts = new float[4];
        this.mAutoScrollEnabled = true;
    }

    public final void addEntry(ArrayList arrayList) {
        LineData lineData = (LineData) this.mData;
        if (lineData == null) {
            return;
        }
        int i = this.mNumOfDataSets;
        int[] iArr = new int[i];
        int i2 = 0;
        for (int i3 = 0; i3 < this.mNumOfDataSets; i3++) {
            LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(i3);
            if (lineDataSet != null) {
                i2 = lineDataSet.mEntries.size();
                iArr[i3] =
                        Math.max(Math.min(((ExtendedBssidInfo) arrayList.get(i3)).rssi, -30), -80);
                lineData.addEntry(new Entry(lineDataSet.mEntries.size(), iArr[i3]), i3);
                lineData.calcMinMax();
                if (lineDataSet.mEntries.size() > 60) {
                    this.mXAxis.mCustomAxisMax = false;
                }
            }
        }
        notifyDataSetChanged();
        invalidate();
        setVisibleXRangeMinimum(60.0f);
        setVisibleXRangeMaximum(60.0f);
        if (this.mAutoScrollEnabled
                || (lineData.getEntryCount() > 0
                        && lineData.getDataSetByIndex(0) != null
                        && ((LineDataSet) lineData.getDataSetByIndex(0)).mEntries.size() <= 60)) {
            moveViewToX(lineData.getEntryCount());
        }
        for (int i4 = 0; i4 < i; i4++) {
            if (iArr[i4] > -70) {
                this.mInWeakAreaNow = false;
                return;
            }
        }
        if (!this.mInWeakAreaNow || this.mWeakAreas.size() <= 0) {
            this.mWeakAreas.add(new Integer[] {Integer.valueOf(i2), Integer.valueOf(i2)});
            SemLog.d("HomeWifi.RssiChart", "New weak signal area from " + i2);
        } else {
            Integer[] numArr =
                    (Integer[]) AlertController$$ExternalSyntheticOutline0.m(1, this.mWeakAreas);
            numArr[1] = Integer.valueOf(i2);
            SemLog.d(
                    "HomeWifi.RssiChart",
                    "Updated weak signal area=(" + numArr[0] + ", " + numArr[1] + ")");
        }
        this.mInWeakAreaNow = true;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase,
              // com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider
    public final BarLineScatterCandleBubbleData getData() {
        return this.mData;
    }

    public final boolean isThereWeakArea() {
        Iterator it = this.mWeakAreas.iterator();
        while (it.hasNext()) {
            Integer[] numArr = (Integer[]) it.next();
            if (numArr[0].intValue() < numArr[1].intValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.github.mikephil.charting.charts.BarLineChartBase, android.view.View
    public final void onDraw(Canvas canvas) {
        Iterator it = this.mWeakAreas.iterator();
        while (it.hasNext()) {
            Integer[] numArr = (Integer[]) it.next();
            this.mConvertedPts[0] = numArr[0].intValue();
            this.mConvertedPts[2] = numArr[1].intValue();
            this.mLeftAxisTransformer.pointValuesToPixel(this.mConvertedPts);
            float[] fArr = this.mConvertedPts;
            float f = fArr[0];
            RectF rectF = this.mViewPortHandler.mContentRect;
            canvas.drawRect(f, rectF.top, fArr[2], rectF.bottom, this.mWeakAreaPaint);
        }
        super.onDraw(canvas);
    }

    public RssiChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColors = new ArrayList();
        this.mWeakAreas = new ArrayList();
        this.mConvertedPts = new float[4];
        this.mAutoScrollEnabled = true;
    }

    public RssiChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColors = new ArrayList();
        this.mWeakAreas = new ArrayList();
        this.mConvertedPts = new float[4];
        this.mAutoScrollEnabled = true;
    }
}
