package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbsXAxisRenderer extends XAxisRenderer {
    public final Context mContext;
    public float[] mPositions;

    public AbsXAxisRenderer(
            Context context,
            XAxis xAxis,
            Transformer transformer,
            ViewPortHandler viewPortHandler) {
        super(viewPortHandler, xAxis, transformer);
        this.mContext = context;
    }

    @Override // com.github.mikephil.charting.renderer.XAxisRenderer
    public void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        XAxis xAxis = this.mXAxis;
        xAxis.getClass();
        this.mPositions = new float[xAxis.mEntryCount * 2];
        int i = 0;
        while (true) {
            float[] fArr = this.mPositions;
            if (i >= fArr.length) {
                this.mTrans.pointValuesToPixel(fArr);
                return;
            } else {
                fArr[i] = xAxis.mEntries[i / 2];
                i += 2;
            }
        }
    }
}
