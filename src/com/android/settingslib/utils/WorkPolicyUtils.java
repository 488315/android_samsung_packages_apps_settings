package com.android.settingslib.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WorkPolicyUtils {
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final PackageManager mPackageManager;
    public final UserManager mUserManager;

    public WorkPolicyUtils(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
    }

    public final int getManagedProfileUserId() {
        Iterator it = this.mUserManager.getAllProfiles().iterator();
        while (it.hasNext()) {
            int identifier = ((UserHandle) it.next()).getIdentifier();
            if (this.mUserManager.isManagedProfile(identifier)) {
                return identifier;
            }
        }
        return -10000;
    }

    public final Intent getWorkPolicyInfoIntentDO() {
        ComponentName deviceOwnerComponentOnAnyUser =
                !this.mPackageManager.hasSystemFeature("android.software.device_admin")
                        ? null
                        : this.mDevicePolicyManager.getDeviceOwnerComponentOnAnyUser();
        if (deviceOwnerComponentOnAnyUser == null) {
            return null;
        }
        Intent intent =
                new Intent("android.settings.SHOW_WORK_POLICY_INFO")
                        .setPackage(deviceOwnerComponentOnAnyUser.getPackageName());
        if (this.mPackageManager.queryIntentActivities(intent, 0).size() != 0) {
            return intent;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0028 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.Intent getWorkPolicyInfoIntentPO() {
        /*
            r6 = this;
            int r0 = r6.getManagedProfileUserId()
            r1 = -10000(0xffffffffffffd8f0, float:NaN)
            r2 = 0
            r3 = 0
            if (r0 != r1) goto Lc
        La:
            r1 = r3
            goto L26
        Lc:
            android.content.Context r1 = r6.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> La
            java.lang.String r4 = r1.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> La
            android.os.UserHandle r5 = android.os.UserHandle.of(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> La
            android.content.Context r1 = r1.createPackageContextAsUser(r4, r2, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> La
            java.lang.String r4 = "device_policy"
            java.lang.Object r1 = r1.getSystemService(r4)
            android.app.admin.DevicePolicyManager r1 = (android.app.admin.DevicePolicyManager) r1
            android.content.ComponentName r1 = r1.getProfileOwner()
        L26:
            if (r1 != 0) goto L29
            return r3
        L29:
            android.content.Intent r4 = new android.content.Intent
            java.lang.String r5 = "android.settings.SHOW_WORK_POLICY_INFO"
            r4.<init>(r5)
            java.lang.String r1 = r1.getPackageName()
            android.content.Intent r1 = r4.setPackage(r1)
            android.content.pm.PackageManager r6 = r6.mPackageManager
            android.os.UserHandle r0 = android.os.UserHandle.of(r0)
            java.util.List r6 = r6.queryIntentActivitiesAsUser(r1, r2, r0)
            int r6 = r6.size()
            if (r6 == 0) goto L49
            return r1
        L49:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.utils.WorkPolicyUtils.getWorkPolicyInfoIntentPO():android.content.Intent");
    }
}
