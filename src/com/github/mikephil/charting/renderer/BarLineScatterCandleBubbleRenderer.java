package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.DataSet$Rounding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BarLineScatterCandleBubbleRenderer extends Renderer {
    public final ChartAnimator mAnimator;
    public Paint mHighlightPaint;
    public final Paint mRenderPaint;
    public final Paint mValuePaint;
    public final XBounds mXBounds;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class XBounds {
        public int max;
        public int min;
        public int range;

        public XBounds() {}

        public final void set(
                BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider,
                LineDataSet lineDataSet) {
            BarLineScatterCandleBubbleRenderer.this.mAnimator.getClass();
            float max = Math.max(0.0f, Math.min(1.0f, 1.0f));
            BarLineChartBase barLineChartBase =
                    (BarLineChartBase) barLineScatterCandleBubbleDataProvider;
            float lowestVisibleX = barLineChartBase.getLowestVisibleX();
            float highestVisibleX = barLineChartBase.getHighestVisibleX();
            Entry entryForXValue =
                    lineDataSet.getEntryForXValue(lowestVisibleX, Float.NaN, DataSet$Rounding.DOWN);
            Entry entryForXValue2 =
                    lineDataSet.getEntryForXValue(highestVisibleX, Float.NaN, DataSet$Rounding.UP);
            this.min = entryForXValue == null ? 0 : lineDataSet.mEntries.indexOf(entryForXValue);
            this.max = entryForXValue2 != null ? lineDataSet.mEntries.indexOf(entryForXValue2) : 0;
            this.range = (int) ((r2 - this.min) * max);
        }
    }

    public BarLineScatterCandleBubbleRenderer(
            ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(viewPortHandler);
        this.mAnimator = chartAnimator;
        Paint paint = new Paint(1);
        this.mRenderPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        new Paint(4);
        Paint paint2 = new Paint(1);
        this.mValuePaint = paint2;
        paint2.setColor(Color.rgb(63, 63, 63));
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setTextSize(Utils.convertDpToPixel(9.0f));
        Paint paint3 = new Paint(1);
        this.mHighlightPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb(255, 187, 115));
        this.mXBounds = new XBounds();
    }

    public abstract void drawData(Canvas canvas);

    public abstract void drawExtras(Canvas canvas);

    public abstract void drawHighlighted(Canvas canvas, Highlight[] highlightArr);

    public final void drawValue(
            Canvas canvas,
            DefaultValueFormatter defaultValueFormatter,
            float f,
            float f2,
            float f3,
            int i) {
        this.mValuePaint.setColor(i);
        canvas.drawText(defaultValueFormatter.mFormat.format(f), f2, f3, this.mValuePaint);
    }

    public abstract void drawValues(Canvas canvas);

    public abstract void initBuffers();

    public final boolean isInBoundsX(
            Entry entry, BarLineScatterCandleBubbleDataSet barLineScatterCandleBubbleDataSet) {
        if (entry == null) {
            return false;
        }
        float indexOf = barLineScatterCandleBubbleDataSet.mEntries.indexOf(entry);
        float size = barLineScatterCandleBubbleDataSet.mEntries.size();
        this.mAnimator.getClass();
        return indexOf < size * 1.0f;
    }
}
