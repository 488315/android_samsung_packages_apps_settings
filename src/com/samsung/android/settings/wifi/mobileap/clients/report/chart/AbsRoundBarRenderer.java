package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.android.settings.R;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbsRoundBarRenderer extends BarChartRenderer {
    public float[] mAdjustedBuffer;
    public BarBuffer mBuffer;
    public final int mEntrySize;
    public int mMissingIndex;
    public float mRadius;
    public final int mSectionEntryCount;
    public int mSectionEntrySize;

    public AbsRoundBarRenderer(
            Context context,
            BarDataProvider barDataProvider,
            ChartAnimator chartAnimator,
            ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mSectionEntryCount = 6;
        this.mChart = barDataProvider;
        this.mEntrySize = 4;
        this.mBarBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBarBorderPaint.setColor(
                context.getResources()
                        .getColor(
                                R.color.dw_data_outline_light_color_no_theme, context.getTheme()));
        this.mBarBorderPaint.setStrokeWidth(
                context.getResources().getDimension(R.dimen.home_screen_time_graph_border));
        this.mBarBorderPaint.setAntiAlias(true);
        new Path();
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer
    public void drawDataSet(Canvas canvas, BarDataSet barDataSet, int i) {
        Transformer transformer =
                ((BarLineChartBase) this.mChart).getTransformer(barDataSet.mAxisDependency);
        this.mAnimator.getClass();
        initBuffers();
        BarBuffer barBuffer = this.mBarBuffers[i];
        this.mBuffer = barBuffer;
        barBuffer.phaseX = 1.0f;
        barBuffer.phaseY = 1.0f;
        ((BarLineChartBase) this.mChart).isInverted(barDataSet.mAxisDependency);
        barBuffer.mInverted = false;
        BarBuffer barBuffer2 = this.mBuffer;
        barBuffer2.mBarWidth = ((BarData) ((BarChart) this.mChart).mData).mBarWidth;
        barBuffer2.feed(barDataSet);
        transformer.pointValuesToPixel(this.mBuffer.buffer);
    }

    @Override // com.github.mikephil.charting.renderer.BarChartRenderer,
              // com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer
    public final void drawValues(Canvas canvas) {
        if (((BarDataSet) ((BarData) ((BarChart) this.mChart).mData).mDataSets.get(0))
                .mDrawValues) {
            super.drawValues(canvas);
        }
    }

    public final void initAdjustedBuffer(int i) {
        int i2;
        int i3;
        float[] fArr = new float[i];
        this.mAdjustedBuffer = fArr;
        float[] fArr2 = this.mBuffer.buffer;
        int i4 = 0;
        if (fArr2.length >= 0) {
            System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
        }
        int i5 = this.mMissingIndex;
        int i6 = this.mSectionEntryCount;
        int i7 = (i6 == 0 || (i3 = this.mSectionEntrySize) < 0) ? 0 : (i5 / i6) * i3;
        int i8 = (i6 == 0 || (i2 = this.mSectionEntrySize) < 0) ? 0 : ((i5 / i6) + 1) * i2;
        float[] fArr3 = this.mAdjustedBuffer;
        int i9 = this.mEntrySize;
        float f =
                i6 + (-2) > 0
                        ? (((fArr3[i9] - fArr3[i9 - 2]) + (fArr3[i9 - 2] - fArr3[0])) * (i6 - 1))
                                / (i6 - 2)
                        : 0.0f;
        int i10 = i7;
        while (i10 < i8) {
            int i11 = this.mEntrySize;
            int i12 = (i11 - 4) + i10;
            if (i12 / i11 != this.mMissingIndex) {
                float[] fArr4 = this.mAdjustedBuffer;
                float f2 = i4 * f;
                fArr4[i12] = fArr4[(i11 - 4) + i7] + f2;
                fArr4[i12 + 2] = fArr4[(i11 - 2) + i7] + f2;
                i4++;
            }
            i10 += i11;
        }
    }
}
