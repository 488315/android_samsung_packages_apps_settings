package com.android.settings.security;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContentProtectionPreferenceUtils {
    public static int getContentProtectionPolicy(Context context, UserHandle userHandle) {
        if (!Flags.manageDevicePolicyEnabled()) {
            return 1;
        }
        if (userHandle != null) {
            try {
                context =
                        context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
            } catch (PackageManager.NameNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return 1;
        }
        return devicePolicyManager.getContentProtectionPolicy(null);
    }
}
