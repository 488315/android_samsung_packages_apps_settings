package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RestrictedLockUtilsInternal$$ExternalSyntheticLambda1
        implements RestrictedLockUtilsInternal.LockSettingCheck {
    public final /* synthetic */ int $r8$classId;

    @Override // com.android.settingslib.RestrictedLockUtilsInternal.LockSettingCheck
    public final boolean isEnforcing(
            DevicePolicyManager devicePolicyManager, ComponentName componentName, int i) {
        switch (this.$r8$classId) {
            case 0:
                if (devicePolicyManager.getPasswordQuality(componentName, i) > 0) {}
                break;
            default:
                if (devicePolicyManager.getMaximumTimeToLock(componentName, i) > 0) {}
                break;
        }
        return false;
    }
}
