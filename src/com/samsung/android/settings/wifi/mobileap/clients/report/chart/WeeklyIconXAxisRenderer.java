package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WeeklyIconXAxisRenderer extends WeeklyXAxisRenderer {
    public ArrayList mIconList;
    public float mIconMargin;
    public int mIconSize;

    @Override // com.samsung.android.settings.wifi.mobileap.clients.report.chart.WeeklyXAxisRenderer, com.samsung.android.settings.wifi.mobileap.clients.report.chart.AbsXAxisRenderer, com.github.mikephil.charting.renderer.XAxisRenderer
    public final void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        Drawable drawable;
        super.drawLabels(canvas, f, mPPointF);
        int i = 0;
        while (true) {
            float[] fArr = this.mPositions;
            if (i >= fArr.length) {
                return;
            }
            float f2 = fArr[i];
            ArrayList arrayList = this.mIconList;
            if (arrayList != null
                    && !arrayList.isEmpty()
                    && (drawable = (Drawable) this.mIconList.get(i / 2)) != null) {
                int i2 = (int) (this.mIconMargin + f);
                int i3 = this.mIconSize;
                DisplayMetrics displayMetrics = Utils.mMetrics;
                MPPointF mPPointF2 = (MPPointF) MPPointF.pool.get();
                int i4 = i3 / 2;
                mPPointF2.x = ((int) f2) - i4;
                mPPointF2.y = i2 - i4;
                Rect rect = Utils.mDrawableBoundsCache;
                drawable.copyBounds(rect);
                int i5 = rect.left;
                int i6 = rect.top;
                drawable.setBounds(i5, i6, i5 + i3, i3 + i6);
                int save = canvas.save();
                canvas.translate(mPPointF2.x, mPPointF2.y);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
            i += 2;
        }
    }
}
