package com.samsung.android.settings.wifi.mobileap.clients.report.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.util.SeslRoundedCorner;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RoundedCornerLinearLayout extends LinearLayout {
    public SeslRoundedCorner mCorners;

    public RoundedCornerLinearLayout(Context context) {
        this(context, null);
        init(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        SeslRoundedCorner seslRoundedCorner = this.mCorners;
        canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
        seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
    }

    public final void init(Context context) {
        try {
            SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context);
            this.mCorners = seslRoundedCorner;
            try {
                seslRoundedCorner.setRoundedCornerColor(
                        15, context.getColor(R.color.sesl_round_and_bgcolor_light));
                this.mCorners.setRoundedCorners(15);
            } catch (NoSuchMethodError e) {
                Log.e("RoundedCorner", e.getMessage());
            }
        } catch (NoSuchMethodError e2) {
            Log.e("RoundedCorner", e2.getMessage());
        }
    }

    public RoundedCornerLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init(context);
    }

    public RoundedCornerLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }
}
