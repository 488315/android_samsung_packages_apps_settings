package com.samsung.android.settings.analyzestorage.presenter.feature;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SepFeatures$FloatingFeature {
    public static final boolean SUPPORT_TRASH =
            !SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_RECYCLE_BIN");
}
