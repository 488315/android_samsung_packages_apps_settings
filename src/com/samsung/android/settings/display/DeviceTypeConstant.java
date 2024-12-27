package com.samsung.android.settings.display;

import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DeviceTypeConstant {
    public static final int getDeviceType() {
        if (Utils.isTablet()) {
            return 2;
        }
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD")) {
            return 1;
        }
        return SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP")
                ? 3
                : 0;
    }
}
