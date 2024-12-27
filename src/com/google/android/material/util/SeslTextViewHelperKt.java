package com.google.android.material.util;

import android.widget.TextView;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SeslTextViewHelperKt {
    public static final void checkMaxFontScale(
            TextView textView, int i, MaxFontScaleRatio maxFontScaleRatio) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        textView.setTextSize(
                0,
                textView.getResources().getDimensionPixelSize(i)
                        * RangesKt.coerceAtMost(
                                textView.getResources().getConfiguration().fontScale,
                                maxFontScaleRatio.getRatio()));
    }
}
