package com.android.settings.biometrics;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.biometrics.ParentalControlsUtilsInternal;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ParentalControlsUtils {
    public static RestrictedLockUtils.EnforcedAdmin parentConsentRequired(Context context, int i) {
        int myUserId = UserHandle.myUserId();
        UserHandle userHandle = new UserHandle(myUserId);
        ComponentName testComponentName =
                ParentalControlsUtilsInternal.getTestComponentName(context, myUserId);
        if (testComponentName == null) {
            return parentConsentRequiredInternal(
                    (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class),
                    i,
                    userHandle);
        }
        Log.d("ParentalControlsUtils", "Requiring consent for test flow");
        return new RestrictedLockUtils.EnforcedAdmin(
                testComponentName, "disallow_biometric", userHandle);
    }

    @VisibleForTesting
    public static RestrictedLockUtils.EnforcedAdmin parentConsentRequiredInternal(
            DevicePolicyManager devicePolicyManager, int i, UserHandle userHandle) {
        if (ParentalControlsUtilsInternal.parentConsentRequired(
                devicePolicyManager, i, userHandle)) {
            return new RestrictedLockUtils.EnforcedAdmin(
                    ParentalControlsUtilsInternal.getSupervisionComponentName(
                            devicePolicyManager, userHandle),
                    "disallow_biometric",
                    userHandle);
        }
        return null;
    }
}
