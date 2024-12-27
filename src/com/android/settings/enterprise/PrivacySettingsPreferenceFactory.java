package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PrivacySettingsPreferenceFactory {
    public static PrivacySettingsPreference createPrivacySettingsPreference(Context context) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        return (devicePolicyManager.isDeviceManaged()
                        && devicePolicyManager.getDeviceOwnerType(
                                        devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                                == 1)
                ? new PrivacySettingsFinancedPreference(context)
                : new PrivacySettingsEnterprisePreference(context);
    }
}
