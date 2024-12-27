package com.android.settings.deviceinfo;

import android.animation.TypeEvaluator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StorageItemPreference$$ExternalSyntheticLambda0
        implements TypeEvaluator {
    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        Long l = (Long) obj2;
        return (f < 1.0f || l.longValue() != 0)
                ? Long.valueOf(
                        ((Long) obj).longValue() + ((long) (f * (l.longValue() - r6.longValue()))))
                : l;
    }
}
