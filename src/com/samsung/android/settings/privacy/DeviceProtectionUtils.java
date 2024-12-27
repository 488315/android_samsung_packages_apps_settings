package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DeviceProtectionUtils {
    public static final Uri DEVICE_PROTECTION_DEEPLINK_URI =
            Uri.parse("devicecare://dc.app_security/security_dashboard");
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.samsung.android.sm.dcapi");

    public static int getDeviceProtectionSecurityInfo(Context context) {
        int i;
        try {
            Bundle call =
                    context.getContentResolver()
                            .call(
                                    AUTHORITY_URI,
                                    "dc_security_threat_found",
                                    (String) null,
                                    (Bundle) null);
            if (call != null) {
                if (call.getBoolean("result")) {
                    int i2 = call.getInt("key_threat_found");
                    Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
                    SemLog.d("SecurityDashboardUtils", "saveDeviceProtectionThreatCount: " + i2);
                    i = 0;
                    SharedPreferences.Editor edit =
                            context.getSharedPreferences("shared_pref_security_dashboard", 0)
                                    .edit();
                    edit.putInt("shared_pref_key_device_protection_threat_count", i2);
                    edit.apply();
                    SemLog.d(
                            "DeviceProtectionUtils",
                            "getDeviceProtectionSecurityInfo returned threatCount = " + i2);
                    if (i2 != 0) {
                        if (i2 > 0) {
                            i = 1;
                        }
                    }
                    SemLog.d(
                            "DeviceProtectionUtils",
                            "getDeviceProtectionSecurityInfo returned statusCode = " + i);
                    return i;
                }
                int i3 = call.getInt("error_id");
                SemLog.d(
                        "DeviceProtectionUtils",
                        "getDeviceProtectionSecurityInfo returned errorId = " + i3);
                if (i3 == 1006) {
                    i = 2;
                    SemLog.d(
                            "DeviceProtectionUtils",
                            "getDeviceProtectionSecurityInfo returned statusCode = " + i);
                    return i;
                }
            }
            i = 3;
            SemLog.d(
                    "DeviceProtectionUtils",
                    "getDeviceProtectionSecurityInfo returned statusCode = " + i);
            return i;
        } catch (IllegalArgumentException e) {
            SemLog.d(
                    "DeviceProtectionUtils",
                    " Unknown authority OR threat_found method not found " + e.getMessage());
            return 3;
        }
    }
}
