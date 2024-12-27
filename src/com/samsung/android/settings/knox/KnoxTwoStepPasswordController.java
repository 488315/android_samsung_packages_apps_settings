package com.samsung.android.settings.knox;

import com.android.internal.widget.LockPatternUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KnoxTwoStepPasswordController {
    public final boolean mDevicePasswordRequirementOnly;
    public final LockPatternUtils mLockPatternUtils;
    public final int mRequestedMinComplexity;
    public final int mUserId;

    public KnoxTwoStepPasswordController(
            int i, int i2, boolean z, LockPatternUtils lockPatternUtils) {
        this.mUserId = i;
        this.mRequestedMinComplexity = i2;
        this.mDevicePasswordRequirementOnly = z;
        this.mLockPatternUtils = lockPatternUtils;
    }
}
