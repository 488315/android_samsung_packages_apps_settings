package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LegendRenderer extends Renderer {
    public List computedEntries;
    public Paint.FontMetrics legendFontMetrics;
    public Legend mLegend;
    public Paint mLegendFormPaint;
    public Paint mLegendLabelPaint;
    public Path mLineFormPath;

    public final void drawForm(
            Canvas canvas, float f, float f2, LegendEntry legendEntry, Legend legend) {
        int i = legendEntry.formColor;
        if (i == 1122868 || i == 1122867 || i == 0) {
            return;
        }
        int save = canvas.save();
        Legend.LegendForm legendForm = legendEntry.form;
        if (legendForm == Legend.LegendForm.DEFAULT) {
            legendForm = legend.mShape;
        }
        this.mLegendFormPaint.setColor(legendEntry.formColor);
        float f3 = legendEntry.formSize;
        if (Float.isNaN(f3)) {
            f3 = legend.mFormSize;
        }
        float convertDpToPixel = Utils.convertDpToPixel(f3);
        float f4 = convertDpToPixel / 2.0f;
        int ordinal = legendForm.ordinal();
        if (ordinal != 2) {
            if (ordinal == 3) {
                this.mLegendFormPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(f, f2 - f4, f + convertDpToPixel, f2 + f4, this.mLegendFormPaint);
            } else if (ordinal != 4) {
                if (ordinal == 5) {
                    float f5 = legendEntry.formLineWidth;
                    if (Float.isNaN(f5)) {
                        f5 = legend.mFormLineWidth;
                    }
                    float convertDpToPixel2 = Utils.convertDpToPixel(f5);
                    DashPathEffect dashPathEffect = legendEntry.formLineDashEffect;
                    if (dashPathEffect == null) {
                        legend.getClass();
                        dashPathEffect = null;
                    }
                    this.mLegendFormPaint.setStyle(Paint.Style.STROKE);
                    this.mLegendFormPaint.setStrokeWidth(convertDpToPixel2);
                    this.mLegendFormPaint.setPathEffect(dashPathEffect);
                    this.mLineFormPath.reset();
                    this.mLineFormPath.moveTo(f, f2);
                    this.mLineFormPath.lineTo(f + convertDpToPixel, f2);
                    canvas.drawPath(this.mLineFormPath, this.mLegendFormPaint);
                }
            }
            canvas.restoreToCount(save);
        }
        this.mLegendFormPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(f + f4, f2, f4, this.mLegendFormPaint);
        canvas.restoreToCount(save);
    }
}
