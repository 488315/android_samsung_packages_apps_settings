package com.samsung.android.settings.accessibility.exclusive.info;

import android.content.Context;

import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class MotionFeatureTaskInfo extends NonAccessibilityExclusiveTaskInfo {
    public String getMotionFeatureName() {
        return getTaskName();
    }

    @Override // com.samsung.android.settings.accessibility.exclusive.info.ExclusiveTaskInfo
    public final boolean isAvailable(Context context) {
        return UsefulfeatureUtils.isSupportMotionFeature(context, getMotionFeatureName());
    }
}
