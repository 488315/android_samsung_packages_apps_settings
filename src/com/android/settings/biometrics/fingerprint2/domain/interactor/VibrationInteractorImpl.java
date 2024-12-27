package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VibrationInteractorImpl {
    static {
        VibrationEffect.createWaveform(new long[] {0, 5, 55, 60}, -1);
        Intrinsics.checkNotNullExpressionValue(
                VibrationAttributes.createForUsage(66), "createForUsage(...)");
        Intrinsics.checkNotNullExpressionValue(
                VibrationAttributes.createForUsage(50), "createForUsage(...)");
        VibrationEffect.get(0);
    }
}
