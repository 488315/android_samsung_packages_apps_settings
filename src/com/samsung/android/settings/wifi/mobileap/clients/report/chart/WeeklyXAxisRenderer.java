package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.android.settings.R;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WeeklyXAxisRenderer extends AbsXAxisRenderer {
    public final float mLabelBgRadius;
    public final int mLabelBgYOffset;
    public final float mLabelMargin;
    public final Paint mLabelPaint;
    public boolean mSelectedInfoLoaded;
    public int mSelectedLabel;
    public final Paint mSelectedLabelBgPaint;
    public final Paint mSelectedLabelPaint;

    public WeeklyXAxisRenderer(
            Context context,
            XAxis xAxis,
            Transformer transformer,
            ViewPortHandler viewPortHandler) {
        super(context, xAxis, transformer, viewPortHandler);
        this.mSelectedInfoLoaded = false;
        Paint paint = new Paint();
        this.mSelectedLabelPaint = paint;
        paint.setColor(
                context.getResources()
                        .getColor(R.color.wifi_ap_marker_text_color_2, context.getTheme()));
        paint.setTextSize(
                context.getResources().getDimension(R.dimen.dw_data_index_selected_text_size));
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
        Paint.Align align = Paint.Align.CENTER;
        paint.setTextAlign(align);
        Paint paint2 = new Paint();
        this.mLabelPaint = paint2;
        paint2.setColor(context.getColor(R.color.wifi_ap_graph_xy_axis_text_color));
        paint2.setTextSize(context.getResources().getDimension(R.dimen.dw_data_index_text_size));
        paint2.setFakeBoldText(false);
        paint2.setAntiAlias(true);
        this.mLabelMargin =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.dashboard_chart_x_label_margin_top);
        paint2.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
        paint2.setTextAlign(align);
        Paint paint3 = new Paint();
        this.mSelectedLabelBgPaint = paint3;
        paint3.setColor(
                context.getResources()
                        .getColor(R.color.wifi_ap_marker_background_color, context.getTheme()));
        paint3.setStyle(Paint.Style.FILL);
        paint3.setAntiAlias(true);
        this.mLabelBgRadius =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.dashboard_chart_selected_label_radius);
        this.mLabelBgYOffset =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.dashboard_chart_selected_label_y_offset);
    }

    @Override // com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsXAxisRenderer,
              // com.github.mikephil.charting.renderer.XAxisRenderer
    public void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        super.drawLabels(canvas, f, mPPointF);
        float f2 = f + this.mLabelMargin;
        int length = this.mPositions.length;
        for (int i = 0; i < length; i += 2) {
            float f3 = this.mPositions[i];
            if (this.mViewPortHandler.isInBoundsX(f3)) {
                XAxis xAxis = this.mXAxis;
                String formattedValue =
                        xAxis.getValueFormatter().getFormattedValue(xAxis.mEntries[i / 2]);
                if (this.mSelectedInfoLoaded && i == this.mSelectedLabel * 2) {
                    canvas.drawCircle(
                            f3,
                            (int) (this.mLabelBgYOffset + f2 + 3.0f),
                            this.mLabelBgRadius,
                            this.mSelectedLabelBgPaint);
                    this.mAxisLabelPaint.set(this.mSelectedLabelPaint);
                } else {
                    this.mAxisLabelPaint.set(this.mLabelPaint);
                }
                drawLabel(canvas, formattedValue, f3, f2, mPPointF);
            }
        }
    }
}
