package com.samsung.android.settings.wifi.develop.weeklyreport.progressbar;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SineInOut90 implements Interpolator {
    public Interpolator mCubic;

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return ((PathInterpolator) this.mCubic).getInterpolation(f);
    }
}
