package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.common.util;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintEnrollOptions;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FingerprintOptionUtilKt {
    public static final void toFingerprintEnrollOptions(Intent intent) {
        int intExtra = intent.getIntExtra("enroll_reason", -1);
        FingerprintEnrollOptions.Builder builder = new FingerprintEnrollOptions.Builder();
        builder.setEnrollReason(0);
        if (intExtra != -1) {
            builder.setEnrollReason(intExtra);
        }
        Intrinsics.checkNotNullExpressionValue(builder.build(), "build(...)");
    }
}
