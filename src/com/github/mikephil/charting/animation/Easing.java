package com.github.mikephil.charting.animation;

import android.animation.TimeInterpolator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Easing {
    public static final AnonymousClass1 EaseInBounce = null;
    public static final AnonymousClass1 EaseOutBounce;
    public static final AnonymousClass1 Linear;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.github.mikephil.charting.animation.Easing$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.github.mikephil.charting.animation.Easing$1] */
    static {
        final int i = 0;
        Linear =
                new TimeInterpolator() { // from class:
                                         // com.github.mikephil.charting.animation.Easing.1
                    @Override // android.animation.TimeInterpolator
                    public final float getInterpolation(float f) {
                        float f2;
                        float f3;
                        switch (i) {
                            case 0:
                                return f;
                            case 1:
                                return 1.0f - Easing.EaseOutBounce.getInterpolation(1.0f - f);
                            default:
                                if (f < 0.36363637f) {
                                    return 7.5625f * f * f;
                                }
                                if (f < 0.72727275f) {
                                    float f4 = f - 0.54545456f;
                                    f2 = 7.5625f * f4 * f4;
                                    f3 = 0.75f;
                                } else if (f < 0.90909094f) {
                                    float f5 = f - 0.8181818f;
                                    f2 = 7.5625f * f5 * f5;
                                    f3 = 0.9375f;
                                } else {
                                    float f6 = f - 0.95454544f;
                                    f2 = 7.5625f * f6 * f6;
                                    f3 = 0.984375f;
                                }
                                return f2 + f3;
                        }
                    }
                };
        final int i2 = 1;
        new TimeInterpolator() { // from class: com.github.mikephil.charting.animation.Easing.1
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2;
                float f3;
                switch (i2) {
                    case 0:
                        return f;
                    case 1:
                        return 1.0f - Easing.EaseOutBounce.getInterpolation(1.0f - f);
                    default:
                        if (f < 0.36363637f) {
                            return 7.5625f * f * f;
                        }
                        if (f < 0.72727275f) {
                            float f4 = f - 0.54545456f;
                            f2 = 7.5625f * f4 * f4;
                            f3 = 0.75f;
                        } else if (f < 0.90909094f) {
                            float f5 = f - 0.8181818f;
                            f2 = 7.5625f * f5 * f5;
                            f3 = 0.9375f;
                        } else {
                            float f6 = f - 0.95454544f;
                            f2 = 7.5625f * f6 * f6;
                            f3 = 0.984375f;
                        }
                        return f2 + f3;
                }
            }
        };
        final int i3 = 2;
        EaseOutBounce =
                new TimeInterpolator() { // from class:
                                         // com.github.mikephil.charting.animation.Easing.1
                    @Override // android.animation.TimeInterpolator
                    public final float getInterpolation(float f) {
                        float f2;
                        float f3;
                        switch (i3) {
                            case 0:
                                return f;
                            case 1:
                                return 1.0f - Easing.EaseOutBounce.getInterpolation(1.0f - f);
                            default:
                                if (f < 0.36363637f) {
                                    return 7.5625f * f * f;
                                }
                                if (f < 0.72727275f) {
                                    float f4 = f - 0.54545456f;
                                    f2 = 7.5625f * f4 * f4;
                                    f3 = 0.75f;
                                } else if (f < 0.90909094f) {
                                    float f5 = f - 0.8181818f;
                                    f2 = 7.5625f * f5 * f5;
                                    f3 = 0.9375f;
                                } else {
                                    float f6 = f - 0.95454544f;
                                    f2 = 7.5625f * f6 * f6;
                                    f3 = 0.984375f;
                                }
                                return f2 + f3;
                        }
                    }
                };
    }
}
