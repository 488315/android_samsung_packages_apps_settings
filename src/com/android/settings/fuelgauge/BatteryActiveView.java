package com.android.settings.fuelgauge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryActiveView extends View {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BatteryActiveProvider {}

    public BatteryActiveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new Paint();
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getWidth() != 0) {
            postInvalidate();
        }
    }

    public void setProvider(BatteryActiveProvider batteryActiveProvider) {
        if (getWidth() != 0) {
            postInvalidate();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {}
}
