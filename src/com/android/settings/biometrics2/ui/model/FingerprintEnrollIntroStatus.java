package com.android.settings.biometrics2.ui.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollIntroStatus {
    public final FingerprintEnrollable enrollableStatus;
    public final boolean mHasScrollToBottom;

    public FingerprintEnrollIntroStatus(boolean z, FingerprintEnrollable enrollableStatus) {
        Intrinsics.checkNotNullParameter(enrollableStatus, "enrollableStatus");
        this.mHasScrollToBottom = z;
        this.enrollableStatus = enrollableStatus;
    }

    public final String toString() {
        return "FingerprintEnrollIntroStatus@"
                + Integer.toHexString(hashCode())
                + "{scrollToBottom:"
                + this.mHasScrollToBottom
                + ", enrollableStatus:"
                + this.enrollableStatus
                + "}";
    }
}
