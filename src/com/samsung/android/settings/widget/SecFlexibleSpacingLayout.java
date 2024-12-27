package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecFlexibleSpacingLayout extends LinearLayout {
    public final int mType;

    public SecFlexibleSpacingLayout(Context context) {
        this(context, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007d  */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r10, int r11) {
        /*
            r9 = this;
            int r0 = r9.getChildCount()
            r1 = 3
            if (r0 != r1) goto Laf
            int r0 = android.view.View.MeasureSpec.getSize(r10)
            float r0 = (float) r0
            android.content.res.Resources r1 = r9.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            float r1 = r1.density
            float r0 = r0 / r1
            int r0 = (int) r0
            android.content.res.Resources r1 = r9.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            int r1 = r1.screenHeightDp
            r2 = 0
            android.view.View r2 = r9.getChildAt(r2)
            r3 = 1
            android.view.View r3 = r9.getChildAt(r3)
            r4 = 2
            android.view.View r5 = r9.getChildAt(r4)
            if (r2 == 0) goto Lb7
            if (r5 == 0) goto Lb7
            if (r3 != 0) goto L39
            goto Lb7
        L39:
            int r6 = r9.mType
            r7 = 1073741824(0x40000000, float:2.0)
            if (r6 != r4) goto L81
            r9.getContext()
            java.lang.StringBuilder r4 = com.android.settings.Utils.sBuilder
            r4 = 480(0x1e0, float:6.73E-43)
            r6 = 1101004800(0x41a00000, float:20.0)
            r8 = 1065353216(0x3f800000, float:1.0)
            if (r0 < r4) goto L52
            r4 = 588(0x24c, float:8.24E-43)
            if (r0 > r4) goto L52
        L50:
            r1 = r8
            goto L74
        L52:
            r4 = 589(0x24d, float:8.25E-43)
            if (r0 < r4) goto L66
            r4 = 959(0x3bf, float:1.344E-42)
            if (r0 > r4) goto L66
            r4 = 411(0x19b, float:5.76E-43)
            if (r1 < r4) goto L62
            r1 = 1063004406(0x3f5c28f6, float:0.86)
            goto L74
        L62:
            r1 = 1058977874(0x3f1eb852, float:0.62)
            goto L74
        L66:
            r1 = 960(0x3c0, float:1.345E-42)
            if (r0 < r1) goto L71
            r1 = 1146224640(0x44520000, float:840.0)
            float r4 = (float) r0
            float r1 = r1 / r4
            r6 = 1107296256(0x42000000, float:32.0)
            goto L74
        L71:
            r6 = 1092616192(0x41200000, float:10.0)
            goto L50
        L74:
            float r6 = r6 * r7
            float r0 = (float) r0
            float r4 = r8 - r1
            float r4 = r4 * r0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L88
            float r6 = r6 / r0
            float r1 = r8 - r6
            goto L88
        L81:
            r9.getContext()
            float r1 = com.android.settings.Utils.getContentFrameWidthRatio(r0, r1)
        L88:
            r0 = 1148846080(0x447a0000, float:1000.0)
            float r1 = r1 * r0
            float r0 = r0 - r1
            float r0 = r0 / r7
            android.view.ViewGroup$LayoutParams r4 = r2.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r4 = (android.widget.LinearLayout.LayoutParams) r4
            android.view.ViewGroup$LayoutParams r6 = r5.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r6 = (android.widget.LinearLayout.LayoutParams) r6
            r4.weight = r0
            r6.weight = r0
            r2.setLayoutParams(r4)
            r5.setLayoutParams(r6)
            android.view.ViewGroup$LayoutParams r0 = r3.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            r0.weight = r1
            r3.setLayoutParams(r0)
            goto Lb7
        Laf:
            java.lang.String r0 = "SecFlexibleSpacingLayout"
            java.lang.String r1 = "should be contained start pane, end pane and content frame."
            android.util.Log.w(r0, r1)
        Lb7:
            super.onMeasure(r10, r11)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.widget.SecFlexibleSpacingLayout.onMeasure(int,"
                    + " int):void");
    }

    public SecFlexibleSpacingLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecFlexibleSpacingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mType = 1;
        setOrientation(0);
    }
}
