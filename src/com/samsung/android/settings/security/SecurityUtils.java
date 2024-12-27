package com.samsung.android.settings.security;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.face.FaceManager;
import android.net.Uri;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.util.SeslKoreanGeneralizer;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecurityUtils {
    public static boolean mFeatureChecked = false;
    public static boolean mFeatureEnabled = false;

    public static boolean ConnectedMobileKeypad(Context context) {
        return context.getResources().getConfiguration().semMobileKeyboardCovered == 1;
    }

    public static boolean checkSAMenuChanged(Context context) {
        Log.e("SecurityUtils", "checkSAMenuChanged.");
        try {
            PackageInfo packageInfo =
                    context.getPackageManager()
                            .getPackageInfo("com.samsung.android.samsungpass", 0);
            PackageInfo packageInfo2 =
                    context.getPackageManager().getPackageInfo("com.osp.app.signin", 0);
            if (packageInfo.versionCode >= 131302200) {
                if (packageInfo2.versionCode >= 220391200) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("SecurityUtils", "Getting VersionCode: NameNotFoundException", e);
        } catch (Exception e2) {
            Log.i("SecurityUtils", "cannot get packageinfo", e2);
        }
        return false;
    }

    public static synchronized boolean hasFingerprintFeature(Context context) {
        synchronized (SecurityUtils.class) {
            if (mFeatureChecked) {
                return mFeatureEnabled;
            }
            boolean hasSystemFeature =
                    context.getPackageManager().hasSystemFeature("android.hardware.fingerprint");
            mFeatureEnabled = hasSystemFeature;
            mFeatureChecked = true;
            return hasSystemFeature;
        }
    }

    public static boolean isBiometricsLockEnabled(Context context) {
        return new LockPatternUtils(context).getBiometricType(UserHandle.myUserId()) != 0;
    }

    public static boolean isEnabledSamsungPass(Context context) {
        if (context != null) {
            return Utils.hasPackage(context, "com.samsung.android.samsungpass")
                    && Utils.hasPackage(context, "com.samsung.android.authfw")
                    && !Utils.isMinimalBatteryUseEnabled(context);
        }
        Log.w("SecurityUtils", "isEnabledSamsungPass : context is NULL");
        return false;
    }

    public static boolean isEnrolledFace(Context context, int i) {
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
        return faceManagerOrNull != null && faceManagerOrNull.hasEnrolledTemplates(i);
    }

    public static boolean isFaceDisabled(Context context, int i) {
        boolean z;
        boolean cameraDisabled;
        if (context == null) {
            Log.d("SecurityUtils", "isFaceDisabled : context is NULL");
            return false;
        }
        boolean isFaceDisabledByDPM = isFaceDisabledByDPM(context, i);
        Log.d("SecurityUtils", "isFaceDisabled by DPM : " + isFaceDisabledByDPM);
        if (Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/PasswordPolicy2",
                        "isBiometricAuthenticationEnabledAsUser",
                        new String[] {String.valueOf(i), String.valueOf(4)})
                == 0) {
            Log.i("SecurityUtils", "isBiometricAuthenticationEnabled(FACE) == Utils.EDM_FALSE");
            z = true;
        } else {
            z = false;
        }
        Log.d("SecurityUtils", "isFaceDisabled by EDM : " + z);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Log.d("SecurityUtils", "isCarmeraDisabledByDPM :  dpm is NULL");
            cameraDisabled = false;
        } else {
            cameraDisabled = devicePolicyManager.getCameraDisabled(null);
        }
        Log.d("SecurityUtils", "isCarmeraDisabledByDPM : " + cameraDisabled);
        Log.d("SecurityUtils", "isFaceDisabled by Camera : " + cameraDisabled);
        boolean z2 = UCMUtils.isUCMKeyguardEnabled() && !UCMUtils.isSupportBiometricForUCM(i);
        Log.d("SecurityUtils", "isFaceDisabled by UCM : " + z2);
        boolean isLockMenuDisabledByEdm = LockUtils.isLockMenuDisabledByEdm(context);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isFaceDisabled by isLockMenuDisabledByEdm : ",
                "SecurityUtils",
                isLockMenuDisabledByEdm);
        return isFaceDisabledByDPM || z || cameraDisabled || z2 || isLockMenuDisabledByEdm;
    }

    public static boolean isFaceDisabledByDPM(Context context, int i) {
        if (context == null) {
            Log.d("SecurityUtils", "isFaceDisabledByDPM : context is NULL");
            return false;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Log.d("SecurityUtils", "isFaceDisabled :  dpm is NULL");
        } else {
            r0 = (devicePolicyManager.getKeyguardDisabledFeatures(null, i) & 128) != 0;
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isFaceDisabled : disableByFaceFlag : ", "SecurityUtils", r0);
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isFaceDisabled by DPM : ", "SecurityUtils", r0);
        return r0;
    }

    public static boolean isFingerprintDisabled(Context context, int i) {
        boolean z;
        boolean isFingerprintDisabledByDPM = isFingerprintDisabledByDPM(context, i);
        if (context == null) {
            Log.d("SecurityUtils", "isFingerprintDisabledByEDM : context is NULL");
        } else if (Utils.getEnterprisePolicyEnabled(
                        context,
                        "content://com.sec.knox.provider/PasswordPolicy2",
                        "isBiometricAuthenticationEnabledAsUser",
                        new String[] {String.valueOf(i), String.valueOf(1)})
                == 0) {
            Log.i(
                    "SecurityUtils",
                    "isBiometricAuthenticationEnabled(FINGERPRINT) == Utils.EDM_FALSE");
            z = true;
            Log.d("SecurityUtils", "isFingerprintDisabled by EDM : " + z);
            boolean z2 =
                    (UCMUtils.isUCMKeyguardEnabled() || UCMUtils.isSupportBiometricForUCM(i))
                            ? false
                            : true;
            Log.d("SecurityUtils", "isFingerprintDisabled by UCM : " + z2);
            boolean isLockMenuDisabledByEdm = LockUtils.isLockMenuDisabledByEdm(context);
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "isFingerprintDisabled by isLockMenuDisabledByEdm : ",
                    "SecurityUtils",
                    isLockMenuDisabledByEdm);
            return !isFingerprintDisabledByDPM ? true : true;
        }
        z = false;
        Log.d("SecurityUtils", "isFingerprintDisabled by EDM : " + z);
        if (UCMUtils.isUCMKeyguardEnabled()) {}
        Log.d("SecurityUtils", "isFingerprintDisabled by UCM : " + z2);
        boolean isLockMenuDisabledByEdm2 = LockUtils.isLockMenuDisabledByEdm(context);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isFingerprintDisabled by isLockMenuDisabledByEdm : ",
                "SecurityUtils",
                isLockMenuDisabledByEdm2);
        return !isFingerprintDisabledByDPM ? true : true;
    }

    public static boolean isFingerprintDisabledByDPM(Context context, int i) {
        boolean z = false;
        if (context == null) {
            Log.d("SecurityUtils", "isFingerprintDisabled : context is NULL");
            return false;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Log.d("SecurityUtils", "isFingerprintDisabled : dpm is NULL");
        } else if ((devicePolicyManager.getKeyguardDisabledFeatures(null, i) & 32) != 0) {
            z = true;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "isFingerprintDisabled by DPM : ", "SecurityUtils", z);
        return z;
    }

    public static boolean isNotAvailableBiometricsWithDexAndMultiWindow(
            Activity activity, int i, String str) {
        if (BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(activity)) {
            return false;
        }
        if (Rune.isSamsungDexMode(activity)) {
            Log.d(str, "isNotAvailableBiometricsWithDexAndMultiWindow : isSamsungDexMode is TRUE.");
            Toast.makeText(
                            activity,
                            new SeslKoreanGeneralizer()
                                    .naturalizeText(
                                            String.format(
                                                    activity.getString(
                                                            R.string.biometircs_dex_cant_use_toast),
                                                    activity.getString(i))),
                            1)
                    .show();
            return true;
        }
        if (!LockUtils.isInMultiWindow(activity)) {
            return false;
        }
        Log.d(str, "isNotAvailableBiometricsWithDexAndMultiWindow : isInMultiWindowMode is TRUE.");
        Toast.makeText(
                        activity,
                        activity.getString(
                                R.string.sec_fingerprint_doesnt_support_multi_window_text),
                        0)
                .show();
        return true;
    }

    public static boolean isSupportFindMyMobileFeature(Context context) {
        Cursor query;
        if (context != null
                && (query =
                                context.getContentResolver()
                                        .query(
                                                Uri.parse(
                                                        "content://com.samsung.android.fmm/fmmsupport"),
                                                null,
                                                null,
                                                null,
                                                null))
                        != null) {
            try {
                int columnIndex = query.getColumnIndex("fmmsupport");
                while (query.moveToNext()) {
                    if (Boolean.valueOf(query.getString(columnIndex)).booleanValue()) {
                        Log.d("SecurityUtils", "Support FMM : " + query.getString(columnIndex));
                        query.close();
                        return true;
                    }
                }
            } finally {
                query.close();
            }
        }
        return false;
    }

    public static boolean isSupportLinkingInfo(Context context) {
        PackageInfo packageInfo;
        if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, 0) >= 30) {
            return false;
        }
        try {
            packageInfo =
                    context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        return packageInfo != null && packageInfo.versionCode >= 540000000;
    }

    public static boolean isWinnerProduct() {
        return SystemProperties.get("ro.product.device", "NONE").trim().startsWith("winner");
    }

    public static void removeBiometricLock(
            Context context, LockPatternUtils lockPatternUtils, int i, int i2) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "removeBiometricLock : ", "SecurityUtils");
        if (lockPatternUtils == null) {
            Log.e("SecurityUtils", "lpu is null!");
        } else {
            lockPatternUtils.setBiometricState(i, 0, i2);
            removeBiometricsLockDBValue(context, i, i2);
        }
    }

    public static void removeBiometricsLockDBValue(Context context, int i, int i2) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "removeBiometricsLockDBValue - LockType : ", "SecurityUtils");
        if (context == null) {
            Log.e("SecurityUtils", "context is null!");
            return;
        }
        if ((i & 1) != 0) {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "fingerprint_screen_lock", 0, i2);
        }
        if ((i & 256) != 0) {
            Settings.Secure.putIntForUser(context.getContentResolver(), "face_screen_lock", 0, i2);
        }
    }

    public static void setBiometricLock(
            Context context, LockPatternUtils lockPatternUtils, int i, int i2) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "setBiometricsLock : ", "SecurityUtils");
        if (lockPatternUtils == null) {
            Log.e("SecurityUtils", "lpu is null!");
        } else {
            lockPatternUtils.setBiometricState(i, 1, i2);
            setBiometricsLockDBValue(context, i, i2);
        }
    }

    public static void setBiometricsLockDBValue(Context context, int i, int i2) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "setBiometricsLockDBValue - LockType : ", "SecurityUtils");
        if (context == null) {
            Log.e("SecurityUtils", "context is null!");
            return;
        }
        if ((i & 1) != 0) {
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "fingerprint_screen_lock", 1, i2);
        }
        if ((i & 256) != 0) {
            Settings.Secure.putIntForUser(context.getContentResolver(), "face_screen_lock", 1, i2);
        }
    }

    public static void setDiagnostic(Context context, boolean z, boolean z2) {
        if (context == null) {
            Log.d("SecurityUtils", "Cannot proceed setDiagnostic api, context is null");
            return;
        }
        Intent intent = new Intent("com.samsung.settings.DIAGNOSTIC_INFO_CHANGED");
        intent.addFlags(32);
        intent.addFlags(16777216);
        Settings.System.putInt(context.getContentResolver(), "samsung_errorlog_agree", z ? 1 : 0);
        intent.putExtra("diagnostic_info_changed", z ? 1 : 0);
        if (isSupportLinkingInfo(context)) {
            Settings.System.putInt(
                    context.getContentResolver(), "samsung_other_info_agree", z2 ? 1 : 0);
            intent.putExtra("diagnostic_other_info_changed", z2 ? 1 : 0);
        }
        context.sendBroadcast(intent);
    }
}
