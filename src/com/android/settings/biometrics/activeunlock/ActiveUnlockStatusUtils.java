package com.android.settings.biometrics.activeunlock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ActiveUnlockStatusUtils {
    public final ContentResolver mContentResolver;
    public final Context mContext;

    public ActiveUnlockStatusUtils(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x005b, code lost:

       if (r4 != false) goto L24;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getAvailability() {
        /*
            r7 = this;
            android.content.Context r0 = r7.mContext
            boolean r0 = com.android.settings.Utils.hasFingerprintHardware(r0)
            if (r0 != 0) goto L12
            android.content.Context r0 = r7.mContext
            boolean r0 = com.android.settings.Utils.hasFaceHardware(r0)
            if (r0 != 0) goto L12
            r7 = 3
            return r7
        L12:
            android.content.Context r0 = r7.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "active_unlock_provider"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r1)
            r1 = 0
            r2 = 0
            java.lang.String r3 = "ActiveUnlockStatusUtils"
            if (r0 != 0) goto L2b
            java.lang.String r0 = "authority not set"
            android.util.Log.i(r3, r0)
        L29:
            r0 = r2
            goto L64
        L2b:
            android.content.Context r4 = r7.mContext
            android.content.pm.PackageManager r4 = r4.getPackageManager()
            r5 = 1048576(0x100000, double:5.180654E-318)
            android.content.pm.PackageManager$ComponentInfoFlags r5 = android.content.pm.PackageManager.ComponentInfoFlags.of(r5)
            android.content.pm.ProviderInfo r4 = r4.resolveContentProvider(r0, r5)
            if (r4 != 0) goto L44
            java.lang.String r0 = "could not find provider"
            android.util.Log.i(r3, r0)
            goto L29
        L44:
            java.lang.String r5 = r4.authority
            boolean r5 = r0.equals(r5)
            if (r5 == 0) goto L5e
            android.content.pm.ApplicationInfo r4 = r4.applicationInfo
            if (r4 != 0) goto L57
            java.lang.String r4 = "application info is null"
            android.util.Log.e(r3, r4)
            r4 = r1
            goto L5b
        L57:
            boolean r4 = r4.isSystemApp()
        L5b:
            if (r4 == 0) goto L5e
            goto L64
        L5e:
            java.lang.String r0 = "authority not valid"
            android.util.Log.e(r3, r0)
            goto L29
        L64:
            if (r0 == 0) goto L6d
            android.content.Intent r7 = r7.getIntent()
            if (r7 == 0) goto L6d
            return r1
        L6d:
            r7 = 2
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.activeunlock.ActiveUnlockStatusUtils.getAvailability():int");
    }

    public final Intent getIntent() {
        boolean isSystemApp;
        String string = Settings.Secure.getString(this.mContentResolver, "active_unlock_target");
        if (string == null) {
            Log.i("ActiveUnlockStatusUtils", "Target action not set");
            return null;
        }
        Intent intent = new Intent(string);
        ActivityInfo resolveActivityInfo =
                intent.resolveActivityInfo(this.mContext.getPackageManager(), 131072);
        if (resolveActivityInfo == null) {
            Log.e("ActiveUnlockStatusUtils", "Target activity not found");
            return null;
        }
        ApplicationInfo applicationInfo = ((ComponentInfo) resolveActivityInfo).applicationInfo;
        if (applicationInfo == null) {
            Log.e("ActiveUnlockStatusUtils", "application info is null");
            isSystemApp = false;
        } else {
            isSystemApp = applicationInfo.isSystemApp();
        }
        if (isSystemApp) {
            Log.i("ActiveUnlockStatusUtils", "Target application is valid");
            return intent;
        }
        Log.e("ActiveUnlockStatusUtils", "Target application is not system");
        return null;
    }

    public final String getTitleForActiveUnlock() {
        boolean hasFaceHardware = Utils.hasFaceHardware(this.mContext);
        boolean hasFingerprintHardware = Utils.hasFingerprintHardware(this.mContext);
        Context context = this.mContext;
        int i = R.string.security_settings_biometric_preference_title;
        if (!hasFaceHardware || !hasFingerprintHardware) {
            if (hasFaceHardware) {
                i = R.string.security_settings_face_preference_title;
            } else if (hasFingerprintHardware) {
                i = R.string.security_settings_fingerprint_preference_title;
            }
        }
        return context.getString(i);
    }

    public final boolean isAvailable() {
        return getAvailability() == 0;
    }
}
