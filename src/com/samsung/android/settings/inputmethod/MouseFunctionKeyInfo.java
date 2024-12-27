package com.samsung.android.settings.inputmethod;

import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManagerGlobal;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.view.SemWindowManager;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class MouseFunctionKeyInfo {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MouseFunctionKeyAppInfo {
        public String mDB;
        public boolean mIsEnabled;
        public CharSequence mLabel;
    }

    public static int getKeycode(int i) {
        switch (i) {
            case 10:
                return 1093;
            case 11:
                return 1094;
            case 12:
                return 1095;
            case 13:
                return 1096;
            default:
                throw new IllegalArgumentException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                                i, "keyCode was wrong. mouseButtonType = "));
        }
    }

    public static String getMouseFunctionDBKey(int i) {
        switch (i) {
            case 10:
                return "mouse_secondary_button_app";
            case 11:
                return "mouse_middle_button_app";
            case 12:
                return "mouse_additional_1_app";
            case 13:
                return "mouse_additional_2_app";
            default:
                return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b5, code lost:

       if (r7 != 3) goto L26;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo
                    .MouseFunctionKeyAppInfo
            getMouseFunctionKeyAppInfo(android.content.Context r7, int r8) {
        /*
            switch(r8) {
                case 10: goto L2a;
                case 11: goto L1e;
                case 12: goto L12;
                case 13: goto L6;
                default: goto L3;
            }
        L3:
            java.lang.String r0 = ""
            goto L35
        L6:
            android.content.ContentResolver r0 = r7.getContentResolver()
            java.lang.String r1 = "mouse_additional_2_app"
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r1)
            goto L35
        L12:
            android.content.ContentResolver r0 = r7.getContentResolver()
            java.lang.String r1 = "mouse_additional_1_app"
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r1)
            goto L35
        L1e:
            android.content.ContentResolver r0 = r7.getContentResolver()
            java.lang.String r1 = "mouse_middle_button_app"
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r1)
            goto L35
        L2a:
            android.content.ContentResolver r0 = r7.getContentResolver()
            java.lang.String r1 = "mouse_secondary_button_app"
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r1)
        L35:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "mouseButtonType : "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r8 = "/ app : "
            r1.append(r8)
            r1.append(r0)
            java.lang.String r8 = r1.toString()
            java.lang.String r1 = "MouseFunctionKeyInfo"
            android.util.Log.i(r1, r8)
            boolean r8 = android.text.TextUtils.isEmpty(r0)
            r1 = 0
            if (r8 == 0) goto L59
            return r1
        L59:
            com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo$MouseFunctionKeyAppInfo r8 = new com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo$MouseFunctionKeyAppInfo
            r8.<init>()
            r8.mLabel = r1
            r1 = 1
            r8.mIsEnabled = r1
            r8.mDB = r0
            r2 = 47
            r3 = 0
            int r2 = r0.indexOf(r2, r3)
            java.lang.String r4 = r0.substring(r3, r2)
            int r2 = r2 + r1
            int r5 = r0.length()
            java.lang.String r0 = r0.substring(r2, r5)
            android.content.pm.PackageManager r2 = r7.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            android.content.ComponentName r5 = new android.content.ComponentName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            r5.<init>(r4, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            r6 = 512(0x200, float:7.175E-43)
            android.content.pm.ActivityInfo r2 = r2.getActivityInfo(r5, r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            android.content.pm.PackageManager r5 = r7.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            java.lang.CharSequence r2 = r2.loadLabel(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            java.lang.String r2 = r2.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            r8.mLabel = r2     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            java.lang.String r2 = com.samsung.android.settings.lockscreen.LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            android.content.pm.PackageManager r2 = r7.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8 java.lang.IllegalArgumentException -> Lba
            int r2 = r2.getApplicationEnabledSetting(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8 java.lang.IllegalArgumentException -> Lba
            r5 = 2
            if (r2 == r5) goto Lba
            r6 = 3
            if (r2 == r6) goto Lba
            android.content.ComponentName r2 = new android.content.ComponentName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            r2.<init>(r4, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            android.content.pm.PackageManager r7 = r7.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8 java.lang.IllegalArgumentException -> Lba
            int r7 = r7.getComponentEnabledSetting(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8 java.lang.IllegalArgumentException -> Lba
            if (r7 == r5) goto Lba
            if (r7 == r6) goto Lba
            goto Lbb
        Lb8:
            r7 = move-exception
            goto Lbe
        Lba:
            r1 = r3
        Lbb:
            r8.mIsEnabled = r1     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lb8
            goto Lc1
        Lbe:
            r7.printStackTrace()
        Lc1:
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo.getMouseFunctionKeyAppInfo(android.content.Context,"
                    + " int):com.samsung.android.settings.inputmethod.MouseFunctionKeyInfo$MouseFunctionKeyAppInfo");
    }

    public static void releaseKeyCustomizationInfo(int i) {
        try {
            WindowManagerGlobal.getWindowManagerService()
                    .removeKeyCustomizationInfo(
                            VolteConstants.ErrorCode.CALL_END_CALL_NW_HANDOVER, 1, getKeycode(i));
            Log.i(
                    "MouseFunctionKeyInfo",
                    "setExtraKeyCustomizationInfo removeKeyCustomizationInfo");
        } catch (RemoteException e) {
            Log.e("MouseFunctionKeyInfo", "failed to removeKeyCustomizationInfo " + e);
        }
    }

    public static void setKeyCustomizationInfo(int i, String str, String str2) {
        Log.i(
                "MouseFunctionKeyInfo",
                "setKeyCustomizationInfo called, pkgName = " + str + " activityName = " + str2);
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(270532608);
            intent.setComponent(new ComponentName(str, str2));
            WindowManagerGlobal.getWindowManagerService()
                    .putKeyCustomizationInfo(
                            new SemWindowManager.KeyCustomizationInfo(
                                    1,
                                    VolteConstants.ErrorCode.CALL_END_CALL_NW_HANDOVER,
                                    getKeycode(i),
                                    1,
                                    intent));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
