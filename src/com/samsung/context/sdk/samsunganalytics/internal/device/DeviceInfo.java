package com.samsung.context.sdk.samsunganalytics.internal.device;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DeviceInfo {
    public final String androidVersion;
    public final String appVersionName;
    public final String deviceModel;
    public final String firmwareVersion;
    public final String language;
    public final String mcc;
    public final String mnc;
    public final String timeZoneOffset;

    public DeviceInfo(Context context) {
        String simOperator;
        this.language = ApnSettings.MVNO_NONE;
        this.androidVersion = ApnSettings.MVNO_NONE;
        this.deviceModel = ApnSettings.MVNO_NONE;
        this.appVersionName = ApnSettings.MVNO_NONE;
        this.mcc = ApnSettings.MVNO_NONE;
        this.mnc = ApnSettings.MVNO_NONE;
        this.timeZoneOffset = ApnSettings.MVNO_NONE;
        this.firmwareVersion = ApnSettings.MVNO_NONE;
        Locale locale = context.getResources().getConfiguration().locale;
        locale.getDisplayCountry();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null
                && (simOperator = telephonyManager.getSimOperator()) != null
                && simOperator.length() >= 3) {
            this.mcc = simOperator.substring(0, 3);
            this.mnc = simOperator.substring(3);
        }
        this.language = locale.getLanguage();
        this.androidVersion = Build.VERSION.RELEASE;
        String str = Build.BRAND;
        this.deviceModel = Build.MODEL;
        this.firmwareVersion = Build.VERSION.INCREMENTAL;
        this.timeZoneOffset =
                String.valueOf(
                        TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset()));
        try {
            this.appVersionName =
                    context.getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0)
                            .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Debug.LogException(DeviceInfo.class, e);
        }
    }
}
