package com.android.settings.applications.appcompat;

import android.R;
import android.app.AppGlobals;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.SparseIntArray;

import androidx.window.WindowProperties;

import com.android.settings.Utils;
import com.android.window.flags.Flags;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAspectRatioManager {
    static final String KEY_ENABLE_USER_ASPECT_RATIO_SETTINGS =
            "enable_app_compat_aspect_ratio_user_settings";
    public final Context mContext;
    public final IPackageManager mIPm;
    public final boolean mIsUserMinAspectRatioAppDefaultFlagEnabled;
    public final Map mUserAspectRatioA11yMap;
    public final Map mUserAspectRatioMap;
    public final SparseIntArray mUserAspectRatioOrder;

    public UserAspectRatioManager(Context context) {
        this(context, AppGlobals.getPackageManager());
    }

    public static boolean isFeatureEnabled(Context context) {
        return DeviceConfig.getBoolean(
                        "window_manager", KEY_ENABLE_USER_ASPECT_RATIO_SETTINGS, true)
                && context.getResources()
                        .getBoolean(R.bool.config_attachNavBarToAppDuringTransition);
    }

    public static Boolean readComponentProperty(
            PackageManager packageManager, String str, String str2) {
        try {
            return Boolean.valueOf(packageManager.getProperty(str2, str).getBoolean());
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final boolean canDisplayAspectRatioUi(ApplicationInfo applicationInfo) {
        return !Boolean.FALSE.equals(
                        readComponentProperty(
                                this.mContext.getPackageManager(),
                                applicationInfo.packageName,
                                WindowProperties.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE))
                && (((LauncherApps) this.mContext.getSystemService(LauncherApps.class))
                                .getActivityList(
                                        applicationInfo.packageName,
                                        UserHandle.getUserHandleForUid(applicationInfo.uid))
                                .isEmpty()
                        ^ true);
    }

    public final CharSequence getAccessibleEntry(int i, String str) {
        int userId = this.mContext.getUserId();
        return (i == 0 && isOverrideToFullscreenEnabled(userId, str))
                ? getAccessibleEntry(6, str)
                : (CharSequence)
                        this.mUserAspectRatioA11yMap.getOrDefault(
                                Integer.valueOf(i), getUserMinAspectRatioEntry(i, userId, str));
    }

    public final String getAspectRatioStringOrDefault(int i, String str) {
        if (str != null) {
            return str;
        }
        switch (i) {
            case 1:
                return this.mContext.getString(
                        com.android.settings.R.string.user_aspect_ratio_half_screen);
            case 2:
                return this.mContext.getString(
                        com.android.settings.R.string.user_aspect_ratio_device_size);
            case 3:
                return this.mContext.getString(com.android.settings.R.string.user_aspect_ratio_4_3);
            case 4:
                return this.mContext.getString(
                        com.android.settings.R.string.user_aspect_ratio_16_9);
            case 5:
                return this.mContext.getString(com.android.settings.R.string.user_aspect_ratio_3_2);
            case 6:
                return this.mContext.getString(
                        com.android.settings.R.string.user_aspect_ratio_fullscreen);
            default:
                return this.mContext.getString(
                        com.android.settings.R.string.user_aspect_ratio_app_default);
        }
    }

    public final String getUserMinAspectRatioEntry(int i, int i2, String str) {
        String aspectRatioStringOrDefault =
                getAspectRatioStringOrDefault(
                        0, (String) ((ArrayMap) this.mUserAspectRatioMap).get(0));
        return !hasAspectRatioOption(i, str)
                ? aspectRatioStringOrDefault
                : (i == 0 && isOverrideToFullscreenEnabled(i2, str))
                        ? getUserMinAspectRatioEntry(6, i2, str)
                        : (String)
                                this.mUserAspectRatioMap.getOrDefault(
                                        Integer.valueOf(i), aspectRatioStringOrDefault);
    }

    public final boolean hasAspectRatioOption(int i, String str) {
        if (i == 6 && !isFullscreenOptionEnabled(str)) {
            return false;
        }
        return ((ArrayMap) this.mUserAspectRatioMap).containsKey(Integer.valueOf(i));
    }

    public boolean isFullscreenOptionEnabled(String str) {
        return !Boolean.FALSE.equals(
                        readComponentProperty(
                                this.mContext.getPackageManager(),
                                str,
                                WindowProperties
                                        .PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE))
                && this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_assistTouchGestureEnabledDefault)
                && DeviceConfig.getBoolean(
                        "window_manager", "enable_app_compat_user_aspect_ratio_fullscreen", true);
    }

    public final boolean isOverrideToFullscreenEnabled(int i, String str) {
        return this.mIsUserMinAspectRatioAppDefaultFlagEnabled
                && hasAspectRatioOption(6, str)
                && !Boolean.FALSE.equals(
                        readComponentProperty(
                                this.mContext.getPackageManager(),
                                str,
                                "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE"))
                && CompatChanges.isChangeEnabled(310816437L, str, UserHandle.of(i));
    }

    public UserAspectRatioManager(Context context, IPackageManager iPackageManager) {
        this.mIsUserMinAspectRatioAppDefaultFlagEnabled = Flags.userMinAspectRatioAppDefault();
        this.mContext = context;
        this.mIPm = iPackageManager;
        this.mUserAspectRatioA11yMap = new ArrayMap();
        this.mUserAspectRatioOrder = new SparseIntArray();
        String[] stringArray =
                context.getResources()
                        .getStringArray(
                                com.android.settings.R.array.config_userAspectRatioOverrideEntries);
        int[] intArray =
                context.getResources()
                        .getIntArray(
                                com.android.settings.R.array.config_userAspectRatioOverrideValues);
        if (stringArray.length != intArray.length) {
            throw new RuntimeException(
                    "config_userAspectRatioOverride options cannot be different length");
        }
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < intArray.length; i++) {
            int i2 = intArray[i];
            String aspectRatioStringOrDefault = getAspectRatioStringOrDefault(i2, stringArray[i]);
            boolean contains = aspectRatioStringOrDefault.contains(":");
            switch (i2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    if (contains) {
                        String[] split = aspectRatioStringOrDefault.split(":");
                        this.mUserAspectRatioA11yMap.put(
                                Integer.valueOf(i2),
                                Utils.createAccessibleSequence(
                                        this.mContext.getString(
                                                com.android.settings.R.string
                                                        .user_aspect_ratio_option_a11y,
                                                split[0],
                                                split[1]),
                                        aspectRatioStringOrDefault));
                    }
                    arrayMap.put(Integer.valueOf(i2), aspectRatioStringOrDefault);
                    this.mUserAspectRatioOrder.put(i2, i);
                    break;
            }
        }
        if (!arrayMap.containsKey(0)) {
            throw new RuntimeException(
                    "config_userAspectRatioOverrideValues options must have"
                        + " USER_MIN_ASPECT_RATIO_UNSET value");
        }
        if (this.mIsUserMinAspectRatioAppDefaultFlagEnabled) {
            arrayMap.put(7, (String) arrayMap.get(0));
            SparseIntArray sparseIntArray = this.mUserAspectRatioOrder;
            sparseIntArray.put(7, sparseIntArray.get(0));
            if (this.mUserAspectRatioA11yMap.containsKey(0)) {
                this.mUserAspectRatioA11yMap.put(
                        7, (CharSequence) this.mUserAspectRatioA11yMap.get(0));
            }
        }
        this.mUserAspectRatioMap = arrayMap;
    }
}
