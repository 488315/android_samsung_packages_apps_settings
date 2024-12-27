package com.google.android.material.datepicker;

import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearSmoothScroller;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SmoothCalendarLayoutManager$1 extends LinearSmoothScroller {
    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 100.0f / displayMetrics.densityDpi;
    }
}
